<template>
    <div>
        <div id="challangeTitle">
            <div>
                내가 참여중인 챌린지
            </div>
        </div>
        <div v-for="(data, idx) in playing" :key="idx" @click="this.$router.push(`/challange/playing/${data.id}`)"
            style="display:flex; justify-content:center;">
            <div class="challangeName">
                <div>
                    {{ data.name }}
                </div>
                <div>
                    >
                </div>
            </div>
        </div>

        <div id="challangeTitle">
            <div>
                참여 가능한 챌린지
            </div>
            <div style="color:#5987DF" @click="this.$router.push('/challange/add')">
                + 추가
            </div>
        </div>


        <div v-for="(data, idx) in beforeStart" :key="idx" @click="this.$router.push(`/challange/before/${data.id}`)"
            style="display:flex; justify-content:center;">
            <div class="challangeName">
                <div>
                    {{ data.name }}
                </div>
                <div>
                    >
                </div>
            </div>
        </div>

        <div id="challangeTitle">
            <div>
                내가 완료한 챌린지
            </div>
        </div>


        <div v-for="(data, idx) in finish" :key="idx" @click="this.$router.push(`/challange/finish/${data.id}`)"
            style="display:flex; justify-content:center;">
            <div class="challangeName">
                <div>
                    {{ data.name }}
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
            beforeStart: [],
            playing: [],
            finish: [],
        }
    },
    methods: {
        getBeforeStart() {
            //시작전 챌린지 리스트 잘됨.
            this.axios({
                method: 'get',
                url: process.env.VUE_APP_API_URL + `/challenge/list`,
            })
                .then((res) => {
                    console.log(res.data)
                    this.beforeStart = res.data
                })
        },
        getPlaying() {
            //시작전 챌린지 리스트 잘됨.
            this.axios({
                method: 'get',
                url: process.env.VUE_APP_API_URL + `/challenge/progress`,
            })
                .then((res) => {
                    console.log(res.data)
                    this.playing = res.data
                })
        },
        getFinish() {
            this.axios({
                method: 'get',
                url: process.env.VUE_APP_API_URL + `/challenge/finish`,
            })
                .then((res) => {
                    console.log(res.data)
                    this.finish = res.data
                })
        }
    },
    mounted() {
        this.getBeforeStart()
        this.getPlaying()
        this.getFinish()
    }

}
</script>

<style>
#challangeTitle {
    border: 2px solid black;
    width: 80%;
    display: flex;
    justify-content: space-between;
    border-radius: 8px;
    padding: 5px 20px;
    font-weight: bold;
    margin: 15px;
}

.challangeName {
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