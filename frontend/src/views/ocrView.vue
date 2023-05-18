<template>
    <div>
      <h1>영수증 촬영</h1>
      <video id="video" autoplay></video>
      <button @click="capture">촬영</button>
      <canvas id="canvas" style="display:none;"></canvas>
    </div>
  </template>
  
  <script>
  export default {
    methods: {
      capture() {
        const video = document.getElementById('video');
        const canvas = document.getElementById('canvas');
        const context = canvas.getContext('2d');
  
        // Draw the video frame to the canvas.
        context.drawImage(video, 0, 0, video.videoWidth, video.videoHeight);
  
        // Convert canvas to blob
        canvas.toBlob((blob) => {
          // Create new form data
          const formData = new FormData();
  
          // Append the image file to the form data
          formData.append('image', blob, 'capture.jpg');
  
          // Send the image to the server
          this.axios.post(process.env.VUE_APP_API_URL + `/api/account-book/ocr`, formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          }).then(response => {
            console.log(response); // 성공 시 처리
          }).catch(error => {
            console.log(error); // 에러 시 처리
          });
        }, 'image/jpeg');
      }
    },
    mounted() {
      const video = document.getElementById('video');
  
      if (navigator.mediaDevices.getUserMedia) {
        navigator.mediaDevices.getUserMedia({ video: true })
          .then((stream) => {
            video.srcObject = stream;
          })
          .catch((error) => {
            console.log("Something went wrong!", error);
          });
      }
    }
  }
  </script>