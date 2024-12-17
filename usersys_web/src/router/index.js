import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '@/views/LoginView.vue'
import RegisterView from '@/views/RegisterView.vue'
import NotFound from '@/views/NotFound.vue'
import store from '@/store/index'
import SellerInfo from '@/views/Seller/SellerInfo.vue'
import SellerMain from '@/views/Seller/SellerMain.vue'
import CustomerMain from '@/views/Customer/CustomerMain.vue'
import CustomerInfo from '@/views/Customer/CustomerInfo.vue'
import AdminMain from '@/views/Administrator/AdminMain.vue'
import CustomerManage from '@/views/Administrator/CustomerManage.vue'
import SellerManage from '@/views/Administrator/SellerManage.vue'
const routes = [
  {
    path: "/",
    name: "Home",
    redirect: "/admin/adminmain/",
    meta: {
      requestAuth: true
    }
  },
  {
    path: "/admin/adminmain/",
    name: "AdminMain",
    component: AdminMain,
    meta: {
      requestAuth: true
    }
  },
  {
    path: "/admin/customermanage/",
    name: "CustomerManage",
    component: CustomerManage,
    meta: {
      requestAuth: true
    }
  },
  {
    path: "/admin/sellermanage/",
    name: "SellerManage",
    component: SellerManage,
    meta: {
      requestAuth: true
    }
  },
  {
    path: "/customer/customermain/",
    name: "CustomerMain",
    component: CustomerMain,
    meta: {
      requestAuth: true
    }
  },
  {
    path: "/update/customerinfo/",
    name: "CustomerInfo",
    component: CustomerInfo,
    meta: {
      requestAuth: true
    }
  },
  {
    path: "/seller/sellermain/",
    name: "SellerMain",
    component: SellerMain,
    meta: {
      requestAuth: true
    }
  },
  {
    path: "/seller/sellerinfo/",
    name: "SellerInfo",
    component: SellerInfo,
    meta: {
      requestAuth: true
    }
  },
  {
    path: "/login/",
    name: "loginview",
    component: LoginView,
  },
  {
    path: "/register/",
    name: "registerview",
    component: RegisterView,
  },
  {
    path: "/404/",
    name: "404",
    component: NotFound,
  },
  {
    path: "/:pathMatch(.*)*",
    redirect: "/404/",
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, from, next) =>{
  if(to.meta.requestAuth && !store.state.user.is_login) {
    next({name: "loginview"});
  }else {
    next();
  }
})

export default router
