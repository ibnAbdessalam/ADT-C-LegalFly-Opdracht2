<template>
  <div id="signinbox" class="flex flex-col items-center justify-center mt-8 space-y-4">
    <button @click="handleSignin" class="px-4 py-2 rounded bg-blue-600 text-white hover:bg-blue-700">
      Sign in
    </button>
    <p v-if="message" class="text-red-600 mt-2">{{ message }}</p>
  </div>
</template>

<script>
import {
  AUTHORIZE_ENDPOINT,
  OAUTH_CLIENT_ID,
  OAUTH_REDIRECT_URI,
  OAUTH_SCOPES
} from "@/constants";
import { createCodeVerifier, createCodeChallenge } from "@/utils/pkce";

export default {
  name: "SigninBox",
  data: () => ({
    message: ""
  }),
  methods: {
    async handleSignin() {
      try {
        // Generate PKCE code verifier + challenge
        const verifier = createCodeVerifier();
        const challenge = await createCodeChallenge(verifier);
        const state = crypto.randomUUID();

        // Save verifier and state for callback
        sessionStorage.setItem("pkce_verifier", verifier);
        sessionStorage.setItem("oauth_state", state);

        // Construct authorization URL
        const params = new URLSearchParams({
          response_type: "code",
          client_id: OAUTH_CLIENT_ID,
          redirect_uri: OAUTH_REDIRECT_URI,
          scope: OAUTH_SCOPES,
          code_challenge: challenge,
          code_challenge_method: "S256",
          state
        });

        // Redirect browser to SAS login page
        window.location.href = `${AUTHORIZE_ENDPOINT}?${params.toString()}`;
      } catch (err) {
        console.error("Sign-in initiation failed:", err);
        this.message = "Kon sign-in niet starten.";
      }
    }
  }
};
</script>

<style scoped>
#signinbox {
  text-align: center;
  margin-top: 2rem;
}
</style>
