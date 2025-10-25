import Row from 'react-bootstrap/Row';
import { Navigate } from 'react-router';
import session from './session';

function Login() {
    if (session.isLoggedIn()) return <Navigate to="/dashboard"/>;
    
    return (
        <>
            <h1>Welcome!</h1>
            <Row><p>{
                (session.hadFailedLogin == true) ?
                    "There was an error while attempting to log you in. Please try again." :
                    "Please log in to continue."
            }</p></Row>
        </>
    )
}

export default Login
