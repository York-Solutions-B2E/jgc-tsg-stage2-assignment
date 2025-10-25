import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import { Navigate } from 'react-router';
import session from './session';

function UserInfo() {
    if (!session.isLoggedIn()) return <Navigate to="/"/>;

    const userInfo = session.getCache("userInfo");

    const showInfoRow = (label, value) => {
        const left0 = 3;
        const left1 = 2;
        const left2 = 1;
        const right0 = 12 - left0;
        const right1 = 12 - left1;
        const right2 = 12 - left2;
        if (label == null) {
            return (<Row><Col
                             xs={{span: right0, offset: left0}}
                             sm={{span: right1, offset: left1}}
                             md={{span: right2, offset: left2}}
                         >{ value }</Col></Row>);
        }
        return (<Row><Col
                         xs={left0}
                         sm={left1}
                         md={left2}
                     ><b>{
                         label
                     }</b></Col><Col
                                    xs={right0}
                                    sm={right1}
                                    md={right2}
                                >{ value }</Col></Row>);
    }
    
    return (
        <>
            <h1>{ `${userInfo.firstName} ${userInfo.lastName}` }</h1>
            <Row><p>NOTE: This information is fake, and for demonstration purposes only.</p></Row>
            { showInfoRow("Email", userInfo.email) }
            { showInfoRow("Phone", userInfo.phone) }
            { showInfoRow("Address", userInfo.mailingAddress.line1) }
            { showInfoRow(null, userInfo.mailingAddress.line2) }
            { showInfoRow(null, `${userInfo.mailingAddress.city}, ${userInfo.mailingAddress.state} ${userInfo.mailingAddress.postalCode}`) }
        </>
    )
}

export default UserInfo
