<template>
    <div class="footer">
        <div style="display:flex; justify-content:space-between; padding: 10px;">
                <div>
                    <p id="footerText">날짜</p>
                </div>
                <div>
                    <p id="footerText">통계</p>
                </div>
                <div>
                    <p id="footerText">자산</p>
                </div>
            
                <div>
                    <p id="footerText">설정</p>
                </div>
            
        </div>
    </div>
</template>

<script>
export default {

}
</script>

<style>
.footer {
    width: 100%;
    max-width: 420px;
    position: sticky;
    bottom: 0;
    height: 80px;
    background-color: #ff9bc2;
    color: rgb(0, 0, 0);
    border-bottom: rgb(220, 220, 220) solid 0.1px;
}
</style>