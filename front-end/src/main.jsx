import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import 'bootstrap/dist/css/bootstrap.min.css';
import App from './App.jsx'
import { GoogleOAuthProvider } from '@react-oauth/google';

createRoot(document.getElementById('root')).render(
    <GoogleOAuthProvider clientId='1087786221035-aci1eaklh0ro25203hjuc3eqi5uej9f8.apps.googleusercontent.com'>
        <StrictMode>
            <App />
        </StrictMode>
    </GoogleOAuthProvider>,
)
