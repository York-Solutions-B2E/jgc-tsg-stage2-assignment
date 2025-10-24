import { useState, useRef, useCallback, useEffect } from 'react';
import reactLogo from './assets/react.svg';
import viteLogo from '/vite.svg';
import './App.css';
import { GoogleLogin } from '@react-oauth/google';
import axios, { HttpStatusCode } from 'axios';

function App() {
    const [count, setCount] = useState(0);
    const failedLoginSteps = useRef(0);
    const [, updateState] = useState();

    // Acknowledge the login failure, so it doesn't appear after manual refresh
    const ackLoginFailure = () => {
        if (failedLoginSteps > 0) {
            failedLoginSteps.current -= 1;
            console.log("Failure decrement: " + failedLoginSteps.current);
        }
    }

    const forceUpdate = useCallback(() => {
        ackLoginFailure();
        updateState({})
    }, []);

    const SESSION_TOKEN_KEY = "tsg_assignment2_auth_key";

    const isLoggedIn = () => {
        return !(localStorage.getItem(SESSION_TOKEN_KEY) == null);
    }

    const checkFailedLogin = () => {
        return (failedLoginSteps.current > 0);
    }

    const markFailedLogin = () => {
        failedLoginSteps.current = 2;
    }

    const createGoogleLoginButton = () => {
        return (<>
            { checkFailedLogin() ? (
                <p>
                    Oops! The login attempt failed to go through.
                    We apologize for the inconvenience.
                    Please refresh the page, or try again later.
                </p>
            ) : <></> }
            <GoogleLogin
                onSuccess={async credentialResponse => {
                    const authToken = credentialResponse.credential;
                    
                    const response = await axios.get(
                        "http://localhost:8080/api/login", {
                            headers: {
                                Authorization: `Bearer ${authToken}`
                            }
                        }
                    );
                    
                    if (response.status === HttpStatusCode.Ok) {
                        localStorage.setItem(SESSION_TOKEN_KEY, authToken);

                        // DEMO CODE
                        const dataResponse1 = await axios.get(
                            "http://localhost:8080/api/auth/me", {
                                headers: {
                                    Authorization: `Bearer ${authToken}`
                                }
                            }
                        );
                        console.log(dataResponse1);
                        const dataResponse2 = await axios.get(
                            "http://localhost:8080/api/dashboard", {
                                headers: {
                                    Authorization: `Bearer ${authToken}`
                                }
                            }
                        );
                        console.log(dataResponse2);
                    }
                    else {
                        markFailedLogin();
                    }
                    
                    forceUpdate();
                }}
                onError={() => {
                    markFailedLogin();
                    forceUpdate();
                }}
            /></>
        );
    }

    return (
        <>
            <div>
                <a href="https://vite.dev" target="_blank">
                    <img src={viteLogo} className="logo" alt="Vite logo" />
                </a>
                <a href="https://react.dev" target="_blank">
                    <img src={reactLogo} className="logo react" alt="React logo" />
                </a>
            </div>
            <h1>Vite + React</h1>
            { isLoggedIn() ? (
                <p>Hello there.</p>
            ) : createGoogleLoginButton() }
            <div className="card">
                <button onClick={() => setCount((count) => count + 1)}>
                    count is {count}
                </button>
                <p>
                    Edit <code>src/App.jsx</code> and save to test HMR
                </p>
            </div>
            <p className="read-the-docs">
                Click on the Vite and React logos to learn more
            </p>
        </>
  )
}

export default App
