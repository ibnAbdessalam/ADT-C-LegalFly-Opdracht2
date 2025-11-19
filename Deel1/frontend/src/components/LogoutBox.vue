<template>
  <div id="logoutbox">
    <button type="button" class="btn btn-warning btn-md" v-on:click="handleLogout">
      Logout
    </button>
  </div>
</template>

<script>
export default {
  methods: {
    handleLogout() {
      this.$parent.username = '';
      localStorage.removeItem('accessToken');
      localStorage.removeItem('idToken');
      localStorage.removeItem('refreshToken');
      // IMPORTANT: perform a top-level navigation so cookies are sent
      window.location.href = "/logout";    }
  }
}
</script>

<style>
#logoutbox {
  float: right;
}
button {
  margin-left: 1em;
}
</style>


