<template>
  <div>
    <div id="groupTitle">
      <div>
        내 그룹
      </div>
      <div style="color:#5987DF" @click="this.$router.push('/group/add')">
        + 추가
      </div>
    </div>


      <div v-for="group in groups" :key="group.id" @click="this.$router.push(`/group/detail/${group.id}`)"
      style="display:flex; justify-content:center;">
        <div class="groupName">
          <div>
            {{ group.name }}
          </div>
          <div>
            >
          </div>
        </div>
      </div>

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

<style>
#groupTitle {
  border: 2px solid black;
  width: 80%;
  display: flex;
  justify-content: space-between;
  border-radius: 8px;
  padding: 5px 20px;
  font-weight: bold;
  margin: 15px;
}

.groupName {
  background-color: #F4F5F7;
  width: 80%;
  display: flex;
  justify-content: space-between;
  border-radius: 8px;
  padding: 10px;
  font-weight: bold;
  margin-top: 10px;
}
</style>