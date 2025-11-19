<template>
  <div id="timesheet">

    <template v-if="!isAuthorized">
      <SignInBox />
    </template>
    <template v-else>
      <div>
        <LogoutBox />
      </div>
      <RequestListView />
    </template>
  </div>
</template>

<script>
// @ is an alias to /src
import SignInBox from "../components/SignInBox.vue";
import axios from "axios";
import RequestListView from "@/views/RequestListView.vue";
import LogoutBox from "@/components/LogoutBox.vue";

import {USER_DETAILS} from "../constants";

export default {
  name: 'Request',
  data: () => ({
    name: '',
    username: ''
  }),
  components: {
    RequestListView,
    SignInBox: SignInBox,
    LogoutBox,

  },
  computed: {
    isAuthorized() {
      return this.username !== "" && this.username !== null;
    }
  },
  methods: {
    fetchUser() {
      axios.get(USER_DETAILS, {headers: {Authorization: `Bearer ${localStorage.getItem('accessToken')}`}})
        .then(response => {
          ({
            name: this.name,
            username: this.username
          } = response.data)
        });
    }
  },
  created() {
    if (localStorage.getItem('accessToken') != null)
      this.fetchUser();
  }

}
</script>
