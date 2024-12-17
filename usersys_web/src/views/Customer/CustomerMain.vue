<template>
    <div class="background">
      <NavBar_C/>
      <div class="container">
          <div class="row">
            <div class="card" style="margin-top: 20px;">
              <div class="card-header">
                用户主页
              </div>
            </div>
          </div>
      </div>
    </div>
      
      
  </template>
  
  <script>
  import NavBar_C from "@/components/NavBar_C";
  
  export default {
    components: {
      NavBar_C
    }
  };
  
  </script>
  <style scoped>
  .background {
      background-image: url("@/assets/绿色护眼.png");
      background-size: cover; /* 背景图填满整个容器 */
      background-repeat: no-repeat; /* 不重复背景图 */
      background-position: center; /* 背景图居中 */
      height: 100vh; /* 高度设置为视口高度 */
  }
  </style>