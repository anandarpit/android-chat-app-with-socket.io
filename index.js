const express = require('express'),
http = require('http'),
app = express(),
server = http.createServer(app),
io = require('socket.io')(server);

  app.get('/',(req,res)=>{
      res.sendFile((__dirname + '/index.html'))
  })

const users = {}

io.on('connection', (socket) => {
console.log('user connected')

    socket.on('join', function(userNickname) {
        console.log(userNickname +" : has joined the chat "  );
        users[socket.id] = userNickname;
        socket.broadcast.emit('userjoinedthechat', userNickname +" : has joined the chat! \n Say Hi");
    })

    socket.on('messagedetection', (senderNickname, messageContent) => {
        console.log(senderNickname+" : " + messageContent)
        let  message = {"message":messageContent, "senderNickname":senderNickname}
        io.emit('message', message )
        })

    socket.on('disconnect', function() {
        console.log(users[socket.id] +' has left ')
        socket.broadcast.emit( "userdisconnect" , users[socket.id])
    })
})

server.listen(3000,()=>{
console.log('Node app is running on port 3000')
})