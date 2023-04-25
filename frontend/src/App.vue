<template>
  <headerComponent/>
  <router-view />
  <footerComponent/>
</template>

<script>
import headerComponent from "@/components/headerComponent.vue";
import footerComponent from "@/components/footerComponent.vue";

export default {
  components: {
    headerComponent,
    footerComponent
  },
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
