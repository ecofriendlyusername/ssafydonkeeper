<template>
    <div>
        <h1>{{ groupName }}</h1>
        
        <button v-if="isLeader" @click="deletGroup(this.groupId)"> 그룹 삭제 </button>
        <button v-if="!isLeader" @click="secession(this.groupId)"> 그룹 탈퇴 </button>

        <ol>
            <li v-for="top in top3" :key="top.id">
                {{ top.email }}
                {{ top.nickname }}
                {{ top.thisMonthTotalAmount }}원
            </li>
        </ol>

        <ul>
            <li v-for="member in allMembers" :key="member.id">
                {{ member.email }}
                {{ member.nickname }}
                {{ member.thisMonthTotalAmount }}원
            </li>
        </ul>
    </div>
</template>
  
<script>
export default {
    data() {
        return {
            groupName : '',
            groupId : -1,
            top3 : [],
            allMembers : [],
            isLeader : false,
        }
    },
    methods : {
        secession (id) {
            this.axios({
                method: 'delete',
                url: process.env.VUE_APP_API_URL + `/circle/secession?circleId=${id}`,
            })
            .then((res) => {
                console.log(res.data)
            })
            .catch(err => { console.log(err) })
        },
        deletGroup (id) {
            this.axios({
                method: 'delete',
                url: process.env.VUE_APP_API_URL + `/circle?circleId=${id}`,
            })
            .then((res) => {
                console.log(res.data)
            })
            .catch(err => { console.log(err) })
        }
    },
    mounted() {
        console.log(this.$route.params.id);
        this.axios.get(process.env.VUE_APP_API_URL + `/circle/${this.$route.params.id}/${new Date().getFullYear()}/${new Date().getMonth()+1}`)
        .then((res) => {
            this.groupName = res.data.name;
            this.top3 = res.data.top3Members;
            this.allMembers = res.data.allMembers;
            this.groupId = res.data.circle_id;

            if (res.data.leader_id == this.$store.state.userData.id) {
                this.isLeader = true;
            }
        })
    }

}
</script>

<style></style>