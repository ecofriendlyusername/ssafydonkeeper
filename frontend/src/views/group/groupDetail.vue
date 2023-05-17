<template>
    <div>

        <div style="display:flex; justify-content: space-between; padding: 0px 50px;">
            <div>
                <h1>{{ groupName }}</h1>
            </div>
            
            <div style="margin-top:20px;">
                <button id="GBtn" v-if="isLeader" @click="this.$router.push(`/group/update/${this.$route.params.id}`)">그룹 수정</button>
                <button id="GBtn" v-if="isLeader" @click="deletGroup(this.$route.params.id)"> 그룹 삭제 </button>
                <button id="GBtn" v-if="!isLeader" @click="secession(this.$route.params.id)"> 그룹 탈퇴 </button>
            </div>
        </div>
        

        <ol style="margin-top:-10px;">
            <div v-for="top in top3" :key="top.id">
                <div class="groupName" style="justify-content:space-between">
                    <div>
                        <span>
                            {{ top.nickname }}
                        </span>
                        <span style="margin-left:5px; color:gray; font-weight: 100; font-size:14px;">
                            {{ top.email }}
                        </span>
                    </div>
                    <div>
                        {{ top.thisMonthTotalAmount }}원
                    </div>
                </div>
            </div>
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

            if (res.data.leader_id == this.$store.state.userData.id) {
                this.isLeader = true;
            }
        })
    }

}
</script>

<style>
#GBtn {
    border: 1px solid black;
    padding:4px 5px;
    margin: 5px;
    border-radius: 5px;
    background-color: white;
}
</style>