import session from './session';
import { Navigate } from 'react-router';

function Logout() {
    if (!session.isLoggedIn()) return <Navigate to="/"/>;

    session.logout();

    setTimeout(() => { window.location.reload(false); }, 500);
    
    return (
        <>
            <h1>Loggging out...</h1>
        </>
    )
}

export default Logout
