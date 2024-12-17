<template>
    <div class="background">
        <NavBar_A/>
        <div class="card">
            <div class="card-header">
                <h1 class="card-title">卖家信息管理</h1>
            </div>
            <div class="card-body">
                <div class="table-container">
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>头像</th>
                                <th>昵称</th>
                                <th>性别</th>
                                <th>生日</th>
                                <th>联系电话</th>
                                <th>邮箱地址</th>
                                <th>简介</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="seller in sellers" :key="seller.sno">
                                <td>
                                    <img :src="seller.avatar" alt="头像" style="width: 50px; height: 50px;">
                                </td>
                                <td>{{ seller.name }}</td>
                                <td>{{ seller.gender }}</td>
                                <td>{{ seller.birth }}</td>
                                <td>{{ seller.number }}</td>
                                <td>{{ seller.email }}</td>
                                <td>{{ seller.description }}</td>
                                <td>
                                    <button type="button" class="btn btn-secondary" style="margin-right: 10px;" data-bs-toggle="modal" :data-bs-target="'#update-seller-modal-' + seller.sno">
                                        修改
                                    </button>
                                    <button type="button" class="btn btn-danger" @click="remove_doc(doc)">
                                        删除
                                    </button>

                                    <!-- Modal -->
                                    <div class="modal fade" :id="'update-seller-modal-' + seller.sno" tabindex="-1">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5">修改卖家用户信息</h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <div class="mb-3">
                                                <label for="update-seller-name" class="form-label">昵称</label>
                                                <input v-model="seller.name" type="text" class="form-control" id="update-seller-name">
                                                </div>
                                                <div class="mb-3">
                                                <label for="update-seller-avatar" class="form-label">头像url</label>
                                                <input v-model="seller.avatar" type="text" class="form-control" id="update-seller-avatar">
                                                </div>
                                                <div class="mb-3">
                                                <label for="update-seller-gender" class="form-label">性别</label>
                                                <input v-model="seller.gender" type="text" class="form-control" id="update-seller-gender">
                                                </div>
                                                <div class="mb-3">
                                                <label for="update-seller-birth" class="form-label">生日</label>
                                                <input v-model="seller.birth" type="text" class="form-control" id="update-seller-birth">
                                                </div>
                                                <div class="mb-3">
                                                <label for="update-seller-number" class="form-label">联系电话</label>
                                                <input v-model="seller.number" type="text" class="form-control" id="update-seller-number">
                                                </div>
                                                <div class="mb-3">
                                                <label for="update-seller-email" class="form-label">邮箱地址</label>
                                                <input v-model="seller.email" type="text" class="form-control" id="update-seller-email">
                                                </div>
                                                <div class="mb-3">
                                                <label for="update-seller-description" class="form-label">简介</label>
                                                <textarea v-model="seller.description" class="form-control" id="update-seller-description" rows="3"></textarea>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <div class="error_message">{{ seller.error_message }}</div>
                                                <button type="button" class="btn btn-primary" @click="update_seller(seller)">保存修改</button>
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                                            </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import $ from "jquery";
import { useStore } from "vuex";
import { ref} from "vue";
import { Modal } from "bootstrap/dist//js/bootstrap";
import NavBar_A from "@/components/NavBar_A.vue";
export default{
    components: {
        NavBar_A,
    },
    setup() {
        const store = useStore();
        let sellers = ref([]);//使用ref包装数据，使其变成响应式数据，可以跟踪变化，初始值为一个空数

        const refresh_sellers = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/seller/get/",
                type: "GET",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,  
                },
                success(resp) {
                    sellers.value = resp;
                }
            })
        }
        refresh_sellers();

        const update_seller = (seller) => {
            $.ajax({
                url: "http://127.0.0.1:3000/seller/update/",
                type: "POST",
                data: {
                    sno: seller.sno,
                    name: seller.name,
                    avatar: seller.avatar,
                    gender: seller.gender,
                    birth: seller.birth,
                    number: seller.number,
                    email: seller.email,
                    description: seller.description
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,  
                },
                success(resp) {
                    if(resp.error_message === "success") {
                        Modal.getInstance(document.querySelector('#update-seller-modal-' + seller.sno)).hide();
                        const backdrop = document.querySelector('.modal-backdrop');
                        if (backdrop) {
                            backdrop.remove(); // 移除模态框的遮罩层
                        }
                        refresh_sellers();
                    } 
                }
            })
        }

        const remove_doc = (doc) => {
            if (confirm("确认删除该单据吗？")) { 
            $.ajax({
                url: "http://127.0.0.1:4000/docs/remove/",
                type: "POST",
                data: {
                    doc_no: doc.dno,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,  
                },
                success(resp) {
                    if(resp.error_message === "success") {
                        alert("删除成功！");
                        refresh_sellers();
                    }
                    else {
                        alert("删除失败：" + resp.error_message);
                    }
                }
            })
        }
            else {
                console.log("用户取消了删除操作");
            }

    }

    return {
            sellers,
            remove_doc,
            update_seller,
            refresh_sellers,
        }
}
// setup 函数中的 return 语句用于将需要在组件模板中使用的数据和方法暴露出来。
// 通过 return 返回的对象中的属性能够被模板直接访问，从而实现响应式更新和交互。
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
div.card {
    margin-top: 20px;
    margin-left: 90px;
    margin-right: 90px;
    
}
.card-title {
    font-size: 145%;
}

.my-custom-button {
    font-size: 15px; /* 文字大小 */
   
    margin-left: 15px;
}
div.error_message {
    color: red;
}

.table-container {
    max-height: 500px; /* 可根据需要调整 */
    overflow-y: auto;  /* 启用垂直滚动 */
    
}

.table thead th {
    position: sticky; /* 使表头粘性 */
    top: 0;          /* 固定在顶部 */
    background-color: #f8f9fa; /* 背景色，与Bootstrap一致 */
    z-index: 2;     /* 确保在内容上方 */
}

</style>
