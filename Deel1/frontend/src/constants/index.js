// export const BACKEND_ROOT_URL = "https://192.168.1.113";

export const FRONTEND_ROOT = window.location.origin;   // https://localhost:8083
export const APP_BASE = "/";
export const API_ROOT      = "http://localhost:8080";   // via nginx reverse proxy naar backend

// OAuth endpoints (via nginx proxy → backend 8082)
export const AUTHORIZE_ENDPOINT = `${API_ROOT}/oauth2/authorize`;
export const TOKEN_ENDPOINT     = `${API_ROOT}/oauth2/token`;

// Public PKCE client (moet overeenkomen met je RegisteredClient in SAS)
export const OAUTH_CLIENT_ID    = "my-client";
export const OAUTH_REDIRECT_URI = `${FRONTEND_ROOT}${APP_BASE}/callback`;
export const OAUTH_SCOPES       = "openid profile";     // voeg extra scopes toe indien nodig

// API endpoints
export const USER_DETAILS = `${API_ROOT}/api/mock`;

