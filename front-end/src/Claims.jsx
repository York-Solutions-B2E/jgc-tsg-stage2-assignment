import Row from 'react-bootstrap/Row';
import { Navigate } from 'react-router';
import session from './session';

function Claims() {
    if (!session.isLoggedIn()) return <Navigate to="/"/>;
    
    return (
        <>
            <h1>Claims</h1>
            <Row><p>This is the claims screen.</p></Row>
        </>
    )
}

export default Claims
