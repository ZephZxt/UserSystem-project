<template>
    <div class="background">
        <NavBar_A/>
        <div class="card">
            <div class="card-header">
                <h1 class="card-title">用户信息管理</h1>
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
                                <th>收件地址</th>
                                <th>联系电话</th>
                                <th>邮箱地址</th>
                                <th>喜欢的东西</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="customer in customers" :key="customer.cno">
                                <td>
                                    <img :src="customer.avatar" alt="头像" style="width: 50px; height: 50px;">
                                </td>
                                <td>{{ customer.name }}</td>
                                <td>{{ customer.gender }}</td>
                                <td>{{ customer.birth }}</td>
                                <td>{{ customer.address }}</td>
                                <td>{{ customer.number }}</td>
                                <td>{{ customer.email }}</td>
                                <td>{{ customer.likes }}</td>
                                <td>
                                    <button type="button" class="btn btn-secondary" style="margin-right: 10px;" data-bs-toggle="modal" :data-bs-target="'#update-customer-modal-' + customer.cno">
                                        修改
                                    </button>
                                    <button type="button" class="btn btn-danger" @click="remove_doc(doc)">
                                        删除
                                    </button>

                                    <!-- Modal -->
                                    <div class="modal fade" :id="'update-customer-modal-' + customer.cno" tabindex="-1">
                                        <div class="modal-dialog">
                                            <div class="modal-content">
                                            <div class="modal-header">
                                                <h1 class="modal-title fs-5">修改普通用户信息</h1>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body">
                                                <div class="mb-3">
                                                <label for="update-customer-name" class="form-label">昵称</label>
                                                <input v-model="customer.name" type="text" class="form-control" id="update-customer-name">
                                                </div>
                                                <div class="mb-3">
                                                <label for="update-customer-avatar" class="form-label">头像url</label>
                                                <input v-model="customer.avatar" type="text" class="form-control" id="update-customer-avatar">
                                                </div>
                                                <div class="mb-3">
                                                <label for="update-customer-gender" class="form-label">性别</label>
                                                <input v-model="customer.gender" type="text" class="form-control" id="update-customer-gender">
                                                </div>
                                                <div class="mb-3">
                                                <label for="update-customer-birth" class="form-label">生日</label>
                                                <input v-model="customer.birth" type="text" class="form-control" id="update-customer-birth">
                                                </div>
                                                <div class="mb-3">
                                                <label for="update-customer-address" class="form-label">收件地址</label>
                                                <input v-model="customer.address" type="text" class="form-control" id="update-customer-address">
                                                </div>
                                                <div class="mb-3">
                                                <label for="update-customer-number" class="form-label">联系电话</label>
                                                <input v-model="customer.number" type="text" class="form-control" id="update-customer-number">
                                                </div>
                                                <div class="mb-3">
                                                <label for="update-customer-email" class="form-label">邮箱地址</label>
                                                <input v-model="customer.email" type="text" class="form-control" id="update-customer-email">
                                                </div>
                                                <div class="mb-3">
                                                <label for="update-customer-likes" class="form-label">喜欢的东西</label>
                                                <textarea v-model="customer.likes" class="form-control" id="update-customer-likes" rows="3"></textarea>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <div class="error_message">{{ customer.error_message }}</div>
                                                <button type="button" class="btn btn-primary" @click="update_customer(customer)">保存修改</button>
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
import { ref, reactive, watch } from "vue";
import { Modal } from "bootstrap/dist//js/bootstrap";
import NavBar_A from "@/components/NavBar_A.vue";
export default{
    components: {
        NavBar_A,
    },
    setup() {
        const store = useStore();
        let customers = ref([]);//使用ref包装数据，使其变成响应式数据，可以跟踪变化，初始值为一个空数组
        const userId = store.state.user.id;
        const userKeyPrefix = `user_${userId}_`;
        const customerKey = `${userKeyPrefix}customeradd`;

        const customerupdate = reactive({
        name: "",
        avatar: "",
        gender: "",
        birth: "",
        address: "",
        number: "",
        email: "",
        likes: "",
        error_message: ""
    });

        const refresh_customers = () => {
            $.ajax({
                url: "http://127.0.0.1:3000/customer/get/",
                type: "GET",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,  
                },
                success(resp) {
                    customers.value = resp; //后端将List转换为json格式，这里直接赋值给docs
// 在 AJAX 请求中，使用 $.ajax 方法时，当请求成功（即 success 回调）时，jQuery 会自动将返回的 JSON 数据解析为 JavaScript 对象或数组。
// 例如，如果后端返回的响应体内容为 JSON 数组，那么 resp 就是一个 JavaScript 数组。你可以直接将这个数组赋值给 docs.value。
                }
            })
        }
        refresh_customers();

        watch(customerupdate, (newValue) => {
        localStorage.setItem(customerKey, JSON.stringify(newValue));
    }, { deep: true }); // deep: true 选项允许观察嵌套对象的变化
        const update_customer = (customer) => {
            $.ajax({
                url: "http://127.0.0.1:3000/customer/update/",
                type: "POST",
                data: {
                    cno: customer.cno,
                    name: customer.name,
                    avatar: customer.avatar,
                    gender: customer.gender,
                    birth: customer.birth,
                    number: customer.number,
                    email: customer.email,
                    address: customer.address,
                    likes: customer.likes,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,  
                },
                success(resp) {
                    if(resp.error_message === "success") {
                        Modal.getInstance(document.querySelector('#update-customer-modal-' + customer.cno)).hide();
                        const backdrop = document.querySelector('.modal-backdrop');
                        if (backdrop) {
                            backdrop.remove(); // 移除模态框的遮罩层
                        }
                        Object.assign(customerupdate, {
                            name: customer.name,
                            avatar: customer.avatar,
                            gender: customer.gender,
                            birth: customer.birth,
                            address: customer.address,
                            number: customer.number,
                            email: customer.email,
                            likes: customer.likes,
                        });
                        
                        refresh_customers();
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
                        refresh_customers();
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
            customers,
            remove_doc,
            update_customer,
            refresh_customers,
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
