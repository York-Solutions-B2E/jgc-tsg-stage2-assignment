import { useState, useRef, useCallback } from 'react';
import { GoogleLogin } from '@react-oauth/google';
import session from './session';
import Container from 'react-bootstrap/Container';
import Col from 'react-bootstrap/Col';
import { Nav, Navbar, NavbarBrand, NavbarCollapse, NavbarToggle, NavLink } from 'react-bootstrap';
import { BrowserRouter, Navigate, Route, Routes } from 'react-router';
import Login from './Login';
import Dashboard from './Dashboard';
import Claims from './Claims';
import UserInfo from './UserInfo';
import Logout from './Logout';

function App() {
    const failedLoginSteps = useRef(0);
    const [, updateState] = useState();
    const [loading, setLoading] = useState(true);

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

    const checkFailedLogin = () => {
        return (failedLoginSteps.current > 0);
    }

    const markFailedLogin = () => {
        failedLoginSteps.current = 2;
    }

    const ComplexGoogleLoginButton = () => {
        return (
            <GoogleLogin
                onSuccess={async (credentialResponse) => {
                    session.login(credentialResponse.credential, () => {
                        // DEMO CODE
                        session.confirmLogin();
                        
                        session.serverGet("dashboard", (data) => {
                            console.log(data);
                        }, (err) => {
                            console.error(err);
                        });
                    }, (err) => {
                        console.error(err);
                        markFailedLogin();
                    }, () => {
                        forceUpdate();
                    });
                }}
                onError={() => {
                    markFailedLogin();
                    forceUpdate();
                }}
            />
        );
    }

    session.hadFailedLogin = checkFailedLogin();

    // Get basic user info
    if (session.isLoggedIn()) {    
        // If our state says we don't need the info,
        // but the session cache says otherwise,
        // then the session cache has authority here.
        const infoName = "userInfo";
        const nextLoading = loading || session.needsCache(infoName);

        if (nextLoading) {
            session.cacheServerInfo("auth/me", infoName, (data) => {
                console.log(data);
            }, (err) => {
                console.error(err);
                session.logout();
                markFailedLogin();
            }, () => {
                setLoading(false);
                forceUpdate();
            });
        
            return (<div>Loading profile...</div>);
        }
        else {

        }
    }

    return (
        <Container fluid>
            <Navbar expand="lg" className="bg-body-tertiary">
                <Container>
                    { session.isLoggedIn() ? (
                        <NavbarToggle xs={1} aria-controls="basic-navbar-nav" />
                    ) : <></> }
                    <NavbarBrand href="./dashboard" xs={9} lg={1}>Benefits</NavbarBrand>
                    { session.isLoggedIn() ? (
                        <NavbarCollapse id="basic-navbar-nav">
                            <Nav className="me-auto">
                                <NavLink href="/dashboard">Dashboard</NavLink>
                                <NavLink href="/me">Profile</NavLink>
                                <NavLink href="/claims">Claims</NavLink>
                                <NavLink href="/logout">Logout</NavLink>
                            </Nav>
                        </NavbarCollapse> ) :
                      (<Col xs={2} lg={2}>
                           <ComplexGoogleLoginButton/>
                       </Col>)
                    }
                </Container>
            </Navbar>
            <main>
                <BrowserRouter>
                    <Routes>
                        <Route path="/dashboard" element={ <Dashboard/> }/>
                        <Route path="/me" element={ <UserInfo/> }/>
                        <Route path="/claims" element={ <Claims/> }/>
                        <Route path="/logout" element={ <Logout/> }/>
                        <Route path="/" element={ <Login/> }/>
                    </Routes>
                </BrowserRouter>
            </main>
        </Container>
    )
}

export default App
