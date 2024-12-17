import $ from 'jquery'

export default {
    state: {
        id: "",
        username: "",
        role: "",
        token: "",
        is_login: false,
        pulling_info: true,
    },
    getters: {
    },
    mutations: {
        updateUser(state, user) {
            state.id = user.id
            state.username = user.username
            state.role = user.role
            state.is_login = true
        },
        updateToken(state, token) {
            state.token = token
        },
        logout(state) {
          state.id = ""
          state.username = ""
          state.role = ""
          state.token = ""
          state.is_login = false

        },
        updatePullingInfo(state, pulling_info) {
          state.pulling_info = pulling_info
      }
    },
    actions: {
        login(context, data) {
            $.ajax({
                url: "http://127.0.0.1:3000/user/token/",
                type:"POST",
                data: {
                  username: data.username,
                  password: data.password,
                },
                success(resp) {
                  if(resp.error_message === "success") {
                    localStorage.setItem("jwt_token", resp.token);
                    context.commit('updateToken', resp.token);
                    data.success(resp);
                  }else {
                    data.error(resp);
                  }
                },
                error(resp) {
                  data.error(resp);
                }
              });
        },
        getInfo(context, data) {
            $.ajax({
                  url: "http://127.0.0.1:3000/user/info/",
                  type:"GET",
                  headers: {
                    Authorization: "Bearer " + context.state.token,
                  },
                  success(resp) {
                    if(resp.error_message === "success") {
                      context.commit("updateUser", {
                        ...resp,
                        is_login: true
                      });
                      data.success(resp);
                    } else {
                      data.error(resp);
                    }
                    
                  },
                  error(resp) {
                    data.error(resp);
                  }
                });
        },
        logout(context) {
          localStorage.removeItem("jwt_token");
          context.commit('logout')
        }
    },
    modules: {
    }
}