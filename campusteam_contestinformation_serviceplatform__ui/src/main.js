import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
/* element-ui */
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
/* 全局css */
import './assets/css/global.css'
/* axios */
import axios from 'axios'
/* qs */
import qs from 'qs'
/* md5 */
import md5 from 'js-md5'
/* moment */
import moment from 'moment'
/* mavonEditor */
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
/* socket.io */
import socket from '@/assets/util/socket';


const TOKEN_INVALID = '认证失败, 请重新登录';
const NETWORK_ERROR = '网络请求异常，请稍后再试'

/* 中文moment */
moment.locale('zh-cn');

// 路由守卫 写在挂载前
router.beforeEach((to, from, next) => {
    /* 在去这些页面前检查登录 */
    if (to.path == '/backstage' || to.path == '/team_hall' || to.path.includes('details')) {
        let token = JSON.parse(window.localStorage.getItem('access-user'))
        if (!token) {
            ElementUI.Message.warning('请先登录')
            next('/login')
        }
    }
    /* 放行 */
    next()
    /* 如果有登录状态 就重连 */
    if (JSON.parse(window.localStorage.getItem('access-user'))) {
        /* socket重连 */
        if (!socket.connected) {
            socket.io.opts.query = "token=" + JSON.parse(localStorage.getItem('access-user'))
            socket.connect()
        }
    }
})

/* 挂载 */
Vue.use(ElementUI)
Vue.use(mavonEditor)
Vue.prototype.$axios = axios
Vue.prototype.$qs = qs
Vue.prototype.$md5 = md5
Vue.prototype.$moment = moment
Vue.prototype.$GLOBALERROR = '系统错误'
Vue.prototype.$EDITSUCCESS = '操作成功'
Vue.prototype.$UPLOADSUCCESS = '上传成功'
// Vue.prototype.$marked = marked

axios.defaults.baseURL = "http://localhost:8088"

/**
 * 请求拦截
 */
axios.interceptors.request.use((req) => {
    console.log('请求拦截{', req.url, '}');
    //TODO: 用正则 匹配更多的开放接口
    if (req.url == '/login' || req.url == '/reg' || req.url.includes('/reg')) {
        return req
    }
    const headers = req.headers;
    if (!headers.Authorization) {
        headers.Authorization = JSON.parse(window.localStorage.getItem("access-user"));
    }
    return req;
})

/**
 * 响应拦截
 */
axios.interceptors.response.use((res) => {
    const { code, message, data } = res.data;
    console.log('响应拦截{', 'code:', code, 'message:', message, 'data:', data, '}');
    if (code === 200 || code === 500400 || code === 500403 || code === 500405 || code === 500406 || code === 500410) {
        return res.data;
    } else {
        /* warning */
        if (code === 500212 || code === 500420 || code === 500421 || code === 500422) {
            ElementUI.Message.warning(message)
            return res.data
        }
        if (message && !message.includes('token')) {
            ElementUI.Message.error(message || NETWORK_ERROR)
        }
        /**
         * token失效 400404
         */
        if (code == 400404) {
            setTimeout(() => {
                window.localStorage.removeItem('access-user')
                router.replace({ path: '/login' });
            }, 1500)
        }
        return res.data;
    }
})

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')

