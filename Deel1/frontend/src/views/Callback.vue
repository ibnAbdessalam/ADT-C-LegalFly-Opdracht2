<!-- src/views/Callback.vue -->
<script>
import { TOKEN_ENDPOINT, OAUTH_CLIENT_ID, OAUTH_REDIRECT_URI } from "@/constants";

export default {
  async mounted() {
    const url   = new URL(window.location.href);
    const code  = url.searchParams.get("code");
    const state = url.searchParams.get("state");
    const exp   = sessionStorage.getItem("oauth_state");

    console.log("Callback.vue mounted");

    if (!sessionStorage.getItem("pkce_verifier")) {
      console.warn("No PKCE verifier in sessionStorage!");
    }

    if (!code || state !== exp) {
      console.error("State mismatch of geen code");
      this.$router.replace("/");
      return;
    }

    const verifier = sessionStorage.getItem("pkce_verifier");

    const body = new URLSearchParams({
      grant_type: "authorization_code",
      code,
      redirect_uri: OAUTH_REDIRECT_URI,
      client_id: OAUTH_CLIENT_ID,
      code_verifier: verifier
    });

    try {
      const res = await fetch(TOKEN_ENDPOINT, {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body
      });
      if (!res.ok) throw await res.json().catch(() => ({ error: res.status }));

      const tokens = await res.json();
      // Snel & simpel: localStorage; (voor productie: BFF overwegen)
      localStorage.setItem("accessToken", tokens.access_token);
      if (tokens.id_token) localStorage.setItem("idToken", tokens.id_token);
      if (tokens.refresh_token) localStorage.setItem("refreshToken", tokens.refresh_token);

      // opruimen
      sessionStorage.removeItem("pkce_verifier");
      sessionStorage.removeItem("oauth_state");

      this.$router.replace("/");
    } catch (e) {
      console.error("Token exchange faalde:", e);
      this.$router.replace("/");
    }
  },
  render() { return null; }
}
</script>