import Row from 'react-bootstrap/Row';
import { Navigate } from 'react-router';
import session from './session';

function Dashboard() {
    if (!session.isLoggedIn()) return <Navigate to="/"/>;
    
    return (
        <>
            <h1>Dashboard</h1>
            <Row><p>This is the dashboard.</p></Row>
        </>
    )
}

export default Dashboard
