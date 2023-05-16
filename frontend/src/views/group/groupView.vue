<template>
  <div>
    <h1>내 그룹</h1>
    <ul>
      <li v-for="group in groups" :key="group.id" @click="this.$router.push(`/group/detail/${group.id}`)">
        {{ group.name }}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  data() {
    return {
      groups: [],
      isLeader: false,
    }
  },
  methods : {
    
  },
  mounted() {
    this.axios.get(process.env.VUE_APP_API_URL + `/circle`)
    .then((res) => {
        this.groups = res.data;
        console.log(res.data);
    })
  }

}
</script>

<style></style>