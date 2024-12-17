<template>
    <div class="background">
        <NavBar_S/>
        <div class="container">
            <div class="row">
                <div class="col-3">
                    <div class="card" style="margin-top: 20px;">
                        <div class="card-body">
                            <h2>头像</h2>
                            <img id="preview" :src="selleradd.avatar" alt="预览图片">
                        </div>
                    </div>
                </div>
                <div class="col-9">
                    <div class="card" style="margin-top: 20px;">
                        <div class="card-header" style="font-size: 120%;">
                            完善卖家个人信息
                            <button type="button" class="btn btn-primary" @click="update_seller()" style="float: right;">保存修改</button>
                        </div>
                        <div class="form-container" style="margin-left: 20px; margin-right: 30px;">
                            <div class="mb-3">
                                <label for="update-seller-name" class="form-label">昵称</label>
                                <input v-model="selleradd.name"  type="text" class="form-control" id="update-seller-name">
                            </div>
                            <div class="mb-3">
                                <label for="update-seller-avatar" class="form-label">头像url</label>
                                <input v-model="selleradd.avatar" type="text" class="form-control" id="update-seller-avatar">
                            </div>
                            <div class="mb-3">
                                <label for="update-seller-gender" class="form-label">性别</label>
                                <input v-model="selleradd.gender" type="text" class="form-control" id="update-seller-gender">
                            </div>
                            <div class="mb-3">
                                <label for="update-seller-birthday" class="form-label">生日</label>
                                <input v-model="selleradd.birth" type="text" class="form-control" id="update-seller-birthday">
                            </div>
                            <div class="mb-3">
                                <label for="update-seller-number" class="form-label">联系电话</label>
                                <input v-model="selleradd.number" type="text" class="form-control" id="update-seller-number">
                            </div>
                            <div class="mb-3">
                                <label for="update-seller-email" class="form-label">邮箱地址</label>
                                <input v-model="selleradd.email" type="email" class="form-control" id="update-seller-email">
                            </div>
                            <div class="mb-3">
                                <label for="update-seller-description" class="form-label">简介</label>
                                <textarea v-model="selleradd.description" class="form-control" id="update-seller-description" rows="3"></textarea>
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
import NavBar_S from "@/components/NavBar_S.vue";
import { useStore } from "vuex";
import {  ref,reactive } from "vue";


export default {
  components: {
    NavBar_S,
  },
  setup(){
    const store = useStore();
    
    let sellers = ref([]);
    const selleradd = reactive({
        name: "",
        avatar: "",
        gender: "",
        birth: "",
        number: "",
        email: "",
        description: "",
        error_message: ""
    });
    
    const update_seller = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/seller/update/",
                type: "POST",
                data: {
                    sno: store.state.user.id,
                    name: selleradd.name,
                    avatar: selleradd.avatar,
                    gender: selleradd.gender,
                    birth: selleradd.birth,
                    number: selleradd.number,
                    email: selleradd.email,
                    description: selleradd.description
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,  
                },
                success(resp) {
                    if(resp.error_message == "success") {
                        alert("修改成功");
                    
                    } else {
                        selleradd.error_message = resp.error_message;
                    }
                }
            })
    }

    const refresh_sellers = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/seller/get/",
                type: "GET",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,  
                },
                success(resp) {
                    sellers.value = resp;
                    selleradd.name = sellers.value[0].name;
                    selleradd.avatar = sellers.value[0].avatar;
                    selleradd.gender = sellers.value[0].gender;
                    selleradd.birth = sellers.value[0].birth;
                    selleradd.number = sellers.value[0].number;
                    selleradd.email = sellers.value[0].email;
                    selleradd.description = sellers.value[0].description;
                    selleradd.error_message = "";
                }
            })
        }
        refresh_sellers();


    return {
        selleradd,
        update_seller,
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