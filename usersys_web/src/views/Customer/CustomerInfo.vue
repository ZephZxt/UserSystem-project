<template>
    <div class="background">
        <NavBar_C/>
        <div class="container">
            <div class="row">
                <div class="col-3">
                    <div class="card" style="margin-top: 20px;">
                        <div class="card-body">
                            <h2>头像</h2>
                            <img id="preview" :src="customeradd.avatar" alt="预览图片">
                        </div>
                    </div>
                </div>
                <div class="col-9">
                    <div class="card" style="margin-top: 20px;">
                        <div class="card-header" style="font-size: 120%;">
                            完善用户个人信息
                            <button type="button" class="btn btn-primary" @click="update_customer()" style="float: right;">保存修改</button>
                        </div>
                        <div class="form-container" style="margin-left: 20px; margin-right: 30px;">
                            <div class="mb-3">
                                <label for="update-customer-name" class="form-label">昵称</label>
                                <input v-model="customeradd.name"  type="text" class="form-control" id="update-customer-name">
                            </div>
                            <div class="mb-3">
                                <label for="update-customer-avatar" class="form-label">头像url</label>
                                <input v-model="customeradd.avatar" type="text" class="form-control" id="update-customer-avatar">
                            </div>
                            <div class="mb-3">
                                <label for="update-customer-gender" class="form-label">性别</label>
                                <input v-model="customeradd.gender" type="text" class="form-control" id="update-customer-gender">
                            </div>
                            <div class="mb-3">
                                <label for="update-customer-birthday" class="form-label">生日</label>
                                <input v-model="customeradd.birth" type="text" class="form-control" id="update-customer-birthday">
                            </div>
                            <div class="mb-3">
                                <label for="update-customer-address" class="form-label">常用收件地址</label>
                                <input v-model="customeradd.address" type="text" class="form-control" id="update-customer-address">
                            </div>
                            <div class="mb-3">
                                <label for="update-customer-number" class="form-label">联系电话</label>
                                <input v-model="customeradd.number" type="text" class="form-control" id="update-customer-number">
                            </div>
                            <div class="mb-3">
                                <label for="update-customer-email" class="form-label">邮箱地址</label>
                                <input v-model="customeradd.email" type="email" class="form-control" id="update-customer-email">
                            </div>
                            <div class="mb-3">
                                <label for="update-customer-likes" class="form-label">喜欢的东西</label>
                                <textarea v-model="customeradd.likes" class="form-control" id="update-customer-likes" rows="3"></textarea>
                            </div>
                        </div>
                        
                    </div>
                </div>
        </div>
        </div>
        
    </div>
    

</template>

<script>
import $ from "jquery";
import NavBar_C from "@/components/NavBar_C.vue";
import { useStore } from "vuex";
import {  reactive, ref } from "vue";

export default {
  components: {
    NavBar_C,
  },
  setup(){
    const store = useStore();
    const customers = ref([]);
    const customeradd = reactive({
        name: "",
        avatar: "",
        gender: "",
        birth: "",
        number: "",
        email: "",
        likes: "",
        error_message: ""
    });
    
    const update_customer = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/customer/update/",
                type: "POST",
                data: {
                    cno: store.state.user.id,
                    name: customeradd.name,
                    avatar: customeradd.avatar,
                    gender: customeradd.gender,
                    birth: customeradd.birth,
                    address: customeradd.address,
                    number: customeradd.number,
                    email: customeradd.email,
                    likes: customeradd.likes,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,  
                },
                success(resp) {
                    if(resp.error_message === "success") {
                        alert("修改成功");
                    
                    } else {
                        customeradd.error_message = resp.error_message;
                    }
                }
            })
    }

    const refresh_customers = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/customer/get/",
                type: "GET",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,  
                },
                success(resp) {
                    customers.value = resp;
                    customeradd.name = customers.value[0].name;
                    customeradd.avatar = customers.value[0].avatar;
                    customeradd.gender = customers.value[0].gender;
                    customeradd.birth = customers.value[0].birth;
                    customeradd.address = customers.value[0].address;
                    customeradd.number = customers.value[0].number;
                    customeradd.email = customers.value[0].email;
                    customeradd.likes = customers.value[0].likes;
                }
            })
        }
        refresh_customers();


    return {
        customeradd,
        update_customer,
    }


  }

}

</script>
<style scoped>
.background {
    background-image: url("@/assets/绿色护眼.png");
    background-size: cover; /* 背景图填满整个容器 */
    background-repeat: no-repeat; /* 不重复背景图 */
    background-position: center; /* 背景图居中 */
    height: 100vh; /* 高度设置为视口高度 */
}
.card-body {
    display: flex;
    flex-direction: column;
    align-items: center; /* 水平居中 */
    justify-content: center; /* 垂直居中（如果需要） */
    text-align: center; /* 使文本内容居中 */
    
}
.form-container {
    height: 500px; 
    overflow-y: auto;
}
</style>