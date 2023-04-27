<template>
    <div class="footer">
        <div style="display:flex; justify-content:space-between; padding: 10px 15px;">
            <div>
                <router-link to="/book/calendar" class="router">
                <div>
                    <img src="@/assets/가계부.png" style="width:35px; margin-bottom: 4px;">
                </div>
                <div>
                    가계부
                </div>
                </router-link>
            </div>

            

            <div>
                <router-link to="/budget" class="router">
                <div>
                    <img src="@/assets/예산.png" style="width:38px; margin-top: 7px; margin-bottom: 8px;">
                </div>
                <div>
                    예산
                </div>
                </router-link>
            </div>

            <div>
                <router-link to="/research" class="router">
                <div>
                    <img src="@/assets/소비패턴.png" style="width:31px; margin-top: 5px; margin-bottom: 3px;">
                </div>
                <div>
                    소비패턴
                </div>
                </router-link>
            </div>

            <div>
                <router-link to="/comparison" class="router">
                <div>
                    <img src="@/assets/자산비교.png" style="width:34px; margin-top: 5px; margin-bottom: 5px;">
                </div>
                <div>
                    자산비교
                </div>
                </router-link>
            </div>
        </div>


        <!-- <div style="display:flex; justify-content:space-between; padding: 10px;">
            <div v-for="(menu, idx) in menuBar" :key="idx">        
                <router-link :to=menu.route style="text-decoration: none; color:black;">{{ menu.name }}</router-link>
            </div>
        </div> -->
    </div>
</template>

<script>
export default {
    name: 'footerComponent',
    data() {
        return {
            menuBar: [
                {name:'가계부', route:'/book/calendar'}, 
                {name:'예산', route:'/budget'}, 
                {name:'소비패턴', route:'/research'}, 
                {name:'자산비교', route:'/comparison'}
            ]
        }
    }
}
</script>

<style>
.footer {
    width: 100%;
    max-width: 420px;
    position: sticky;
    bottom: 0;
    height: 80px;
    background-color: #EFEFEF;
    color: rgb(0, 0, 0);
    border-bottom: rgb(220, 220, 220) solid 0.1px;
    margin-top: 30px;
}

.router {
    text-decoration: none;
    color: #787878;
    font-weight: bold;
}
</style>