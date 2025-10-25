import axios, { HttpStatusCode } from 'axios';

const SESSION_KEY = "tsg_assignment2_session_key";
const ENDPOINT_ROOT = "http://localhost:8080/api";

class Session {
    constructor() {
        if (Session.instance) {
            return Session.instance;
        }
        
        Session.instance = this;

        this.blockingTaskCount = 0;
        this.hasEnsuredState = false;
        this.blockingTaskResolvers = new Set();

        this.ensureState();
    }

    // Sometimes we can't present information instantaneously,
    // so we have a system for presenting loading screens in between.
    async doBlockingTask(task) {
        this.blockingTaskCount++;

        await task();

        this.blockingTaskCount--;

        if (this.blockingTaskCount > 0) return;
        
        for (const resolve of this.blockingTaskResolvers) {
            resolve();
        }
        this.blockingTaskResolvers.clear();
    }

    async awaitBlockingTask() {
        if (this.blockingTaskCount == 0) return;
        await (new Promise(resolve => {
            this.blockingTaskResolvers.add(resolve);
        }));
    }

    endBlockingTask() {
        this.blockingTaskCount--;
    }
    
    async ensureState() {
        if (this.hasEnsuredState) return;
        this.doBlockingTask(async () => {
            this.hasEnsuredState = true;
        
            const storedSession = JSON.parse(localStorage.getItem(SESSION_KEY));
            if (storedSession) {
                this.storedState = storedSession;
            }
            else {
                this.resetState();
            }
        });
    }

    saveState() {
        localStorage.setItem(SESSION_KEY, JSON.stringify(this.storedState));
    }

    confirmLogin() {
        this.storedState.isLoggedIn = true;
        this.saveState();
    }

    isLoggedIn() {
        if (!this.storedState) return false;
        if (!this.storedState.authToken) return false;
        if (!(this.storedState.authToken.length > 0)) return false;
        if (this.storedState.isLoggedIn != true) return false;

        return true;
    }

    // Pass the authentication token from Google to the server,
    // which will either prepare a new user, or ensure an existing one.
    async login(authToken, onSuccess, onError, onAnyways) {
        this.serverGet("login", () => {
            this.storedState.authToken = authToken;
            this.saveState();
            onSuccess();
        }, (err) => {
            onError(err);
        }, onAnyways, authToken);
    }

    // If we have not already fetched the data before,
    // fetch a new copy and cache it.
    async cacheServerInfo(path, propName, onSuccess, onError, onAnyway) {
        if (this.storedState[propName]) {
            if (onSuccess) {
                onSuccess(this.storedState[propName]);
            }
            if (onAnyway) {
                onAnyway();
            }
            return;
        }
        
        this.serverGet(path, (data) => {
            this.storedState[propName] = data;
            this.saveState();
            if (onSuccess) {
                onSuccess(data);
            }
        }, onError, onAnyway);
    }

    getCache(propName) {
        if (this.storedState == null) return null;
        return this.storedState[propName];
    }

    needsCache(propName) {
        if (this.storedState == null) return true;
        if (this.storedState[propName] == null) return true;
        return false;
    }

    // Make a GET call to the back-end, according to /api/path
    //
    // If onSuccess is defined, it runs after an OK is received.
    // If onError is defined, it runs when anything goes wrong.
    // If onAnyway is defined, it runs last, no matter what happens.
    async serverGet(path, onSuccess, onError, onAnyway, authToken) {
        this.doBlockingTask(async () => {
            if (authToken == null) {
                authToken = this.storedState.authToken;
            }
            let errorStatus = null;
        
            try {
                const response = await axios.get(
                    `${ENDPOINT_ROOT}/${path}`, {
                        headers: {
                            Authorization: `Bearer ${authToken}`
                        }
                    }
                );

                if (response.status === HttpStatusCode.Ok) {
                    if (onSuccess) {
                        onSuccess(response.data);
                    }
                    if (onAnyway) {
                        onAnyway();
                    }
                    return;
                }

                errorStatus = response.status;
            } catch (err) {
                if (onError) {
                    onError(err);
                }
                else {
                    console.error(err);
                }
                if (onAnyway) {
                    onAnyway();
                }
                return;
            }

            const newError = new Error("Oopth, something went generically wrong! " + errorStatus);
            if (onError) {
                onError(newError);
            }
            else {
                console.error(newError);
            }
            if (onAnyway) {
                onAnyway();
            }
        });
    }

    logout() {
        this.resetState();
        this.saveState();
    }

    resetState() {
        this.storedState = { };
    }
}

export default new Session();
