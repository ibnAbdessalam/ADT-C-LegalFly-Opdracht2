import { createRouter, createWebHistory } from "vue-router";
import RequestDetailView from "../views/RequestDetailView.vue";
import Home from "@/components/Home.vue";
import Callback from "@/views/Callback.vue";
import AddRequestView from "../views/AddRequestView.vue";

const routes = [
  {
    path: "/",
    name: "request-list",
    component: Home,
  },
  {
    path: "/request/:id",
    name: "request-detail",
    component: RequestDetailView,
  },
  {
    path: "/callback",
    name: "Callback",
    component: Callback,
   },
  {
    path: "/add-request",
    name: "add-request",
    component: AddRequestView,
  }
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
