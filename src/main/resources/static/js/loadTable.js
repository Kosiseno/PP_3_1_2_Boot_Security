let url = 'http://localhost:8080/alll';
fetch(url).then(
    res=>{
        res.json().then(
            data=>{
                if(data.length > 0){
                    var temp = "";

                    data.forEach((user)=>{
                        temp += "<tr>";
                        temp += "<td>" + user.id + "</td>";
                        temp += "<td>" + user.username + "</td>";
                        temp += "<td>" + user.firstName + "</td>";
                        temp += "<td>" + user.lastName + "</td>";
                        temp += "<td>" + user.age + "</td>";
                        temp += "<td>" + user.roles[0].roleName + "</td>";
                    })
                    document.getElementById("userTableBody").innerHTML = temp
                }
            }
        )
    }
)
