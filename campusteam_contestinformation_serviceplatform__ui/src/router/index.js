import Vue from 'vue'
import VueRouter from 'vue-router'
/* element-ui */
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';

const TOKEN_INVALID = '请先登录';

// 解决报错
const originalPush = VueRouter.prototype.push;
const originalReplace = VueRouter.prototype.replace;
// push
VueRouter.prototype.push = function push(location, onResolve, onReject) {
  if (onResolve || onReject)
    return originalPush.call(this, location, onResolve, onReject);
  return originalPush.call(this, location).catch((err) => err);
};
// replace
VueRouter.prototype.replace = function push(location, onResolve, onReject) {
  if (onResolve || onReject)
    return originalReplace.call(this, location, onResolve, onReject);
  return originalReplace.call(this, location).catch((err) => err);
};


Vue.use(VueRouter)



const routes = [
  {
    path: '/',
    name: 'Main',
    component: () => import(/* webpackChunkName: "about" */ '../views/Main.vue')
  },
  {
    path: '/main',
    name: 'Main',
    component: () => import(/* webpackChunkName: "about" */ '../views/Main.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import(/* webpackChunkName: "about" */ '../views/Login.vue')
  },
  {
    path: '/reg',
    name: 'Reg',
    component: () => import(/* webpackChunkName: "about" */ '../views/Reg.vue')
  },
  {
    path: '/competition_hall',
    name: 'CompetitionHall',
    component: () => import(/* webpackChunkName: "about" */ '../views/CompetitionHall.vue')
  },
  {
    path: '/competition_details',
    name: 'CompetitionDetails',
    component: () => import(/* webpackChunkName: "about" */ '../views/CompetitionDetails.vue')
  },
  {
    path: '/activity_hall',
    name: 'ActivityHall',
    component: () => import(/* webpackChunkName: "about" */ '../views/ActivityHall.vue')
  },
  {
    path: '/activity_details',
    name: 'ActivityDetails',
    component: () => import(/* webpackChunkName: "about" */ '../views/ActivityDetails.vue')
  },
  {
    path: '/team_hall',
    name: 'TeamHall',
    component: () => import(/* webpackChunkName: "about" */ '../views/TeamHall.vue')
  },
  {
    path: '/team_details',
    name: 'TeamDetails',
    component: () => import(/* webpackChunkName: "about" */ '../views/TeamDetails.vue')
  },
  {
    path: '/bbs',
    name: 'BBS',
    component: () => import(/* webpackChunkName: "about" */ '../views/BBS.vue')
  },
  {
    path: '/article_details',
    name: 'ArticleDetails',
    component: () => import(/* webpackChunkName: "about" */ '../views/ArticleDetails.vue')
  },
  {
    path: '/article_add',
    name: 'ArticleAdd',
    component: () => import(/* webpackChunkName: "about" */ '../components/backstage/ArticleAdd.vue')
  },
  {
    path: '/user_details',
    name: 'UserDetails',
    component: () => import(/* webpackChunkName: "about" */ '../views/UserDetails.vue')
  },
  {
    path: '/backstage',
    name: 'BackStage',
    component: () => import(/* webpackChunkName: "about" */ '../views/BackStage.vue')
  },
  {
    path: '/n_a_details',
    name: 'NADetails',
    component: () => import(/* webpackChunkName: "about" */ '../views/NADetails.vue')
  },
  {
    path: '/news_add',
    name: 'NewsAdd',
    component: () => import(/* webpackChunkName: "about" */ '../components/backstage/NewsAdd.vue')
  },
  {
    path: '/announcement_add',
    name: 'AnnouncementAdd',
    component: () => import(/* webpackChunkName: "about" */ '../components/backstage/AnnouncementAdd.vue')
  },
]

const router = new VueRouter({
  routes
})

export default router

