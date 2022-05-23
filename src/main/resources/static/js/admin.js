const url = 'http://localhost:8080/api/users/';
const urlRoles = 'http://localhost:8080/api/roles/';
let container = document.getElementById("userTableBody");
const newUserForm = document.getElementById('newUserForm');
const editUserForm = document.getElementById('editUserForm');
const deleteUserForm = document.getElementById('deleteUserForm');

const btnCreate = document.getElementById('new-user-tab');
const newRoles = document.getElementById('newRoles');

let result = '';
//модальное окно edit
let editUserModal = new bootstrap.Modal(document.getElementById('editUserModal'));
//модальное окно delete
let deleteUserModal = new bootstrap.Modal(document.getElementById('deleteUserModal'));

//для взятия данных с форм изменение пользователя
const editId = document.getElementById('editId');
const editUsername = document.getElementById('editUsername');
const editFirstName = document.getElementById('editFirstName');
const editLastName = document.getElementById('editLastName');
const editAge = document.getElementById('editAge');
const editPassword = document.getElementById('editPassword');
const editRoles = document.getElementById('editRoles');

//для взятия данных с форм удаление пользователя
const delId = document.getElementById('delId');
const delUsername = document.getElementById('delUsername');
const delFirstName = document.getElementById('delFirstName');
const delLastName = document.getElementById('delLastName');
const delAge = document.getElementById('delAge');
const delRoles = document.getElementById('delRoles');

//для взятия данных с форм создания пользователя
const newUsername = document.getElementById('newUsername');
const newFirstName = document.getElementById('newFirstName');
const newLastName = document.getElementById('newLastName');
const newAge = document.getElementById('newAge');
const newPassword = document.getElementById('newPassword');

//для переключения страниц админа/пользователя
const firstTabPill = document.querySelector('.ddpills');
const firstTab = document.querySelector('.ddtabs');

//массив ролей для контейнера в создании/редактировании/удалении пользователя
let rolesArr = [];

//для переключения между "табами"
const navtab1 = document.getElementById('all-users-tab');
const navtab2 = document.getElementById('new-user-tab');
const tab1 = document.getElementById('all-users');
const tab2 = document.getElementById('new-user');


let renderUsers = (users) => {
    users.forEach(user => {
        let roles = '';
        user.roles.forEach(
            role => {
                r = role.roleName.substring(5);
                roles += r + ' ';
            }
        );
        result += `
            <tr>
                <td>${user.id}</td>
                <td>${user.username}</td>
                <td>${user.firstName}</td>
                <td>${user.lastName}</td>
                <td>${user.age}</td>               
                <td>${roles}</td>
                <td><a class="btnEdit btn btn-sm btn-info text-white">Edit</a></td>
                <td><a class="btnDelete btn btn-danger btn-sm">Delete</a></td>
            </tr>
            `

    })
    container.innerHTML = result;

}

const renderRoles = (roles) => {
    let rolesOptions = '';
    roles.forEach(role => {

        rolesOptions += `
            <option value = ${role.id}>${role.roleName.substring(5)}</option>
            `
        rolesArr.push(role);
    })
    newRoles.innerHTML = rolesOptions;
    editRoles.innerHTML = rolesOptions;
    delRoles.innerHTML = rolesOptions;
}


fetch(url)
    .then(res => res.json())
    .then(data => renderUsers(data))
    .catch(error => console.log(error));

let allRoles;

fetch(urlRoles)
    .then(res => res.json())
    .then(data => {
        allRoles = data;
        renderRoles(allRoles)
    });


const refreshListOfUsers = () => {
    fetch(url)
        .then(res => res.json())
        .then(data => {
            result = '';
            renderUsers(data)
        })
}

const on = (element, event, selector, handler) => {
    element.addEventListener(event, e => {
        if (e.target.closest(selector)) {
            handler(e)
        }
    })
}


on(document, 'click', '.btnDelete', e => {
    const row = e.target.parentNode.parentNode;
    idForm = row.children[0].innerHTML;

    const usernameForm = row.children[1].innerHTML;
    const firstNameForm = row.children[2].innerHTML;
    const lastNameForm = row.children[3].innerHTML;
    const ageForm = row.children[4].innerHTML;


    delId.value = idForm;
    delUsername.value = usernameForm;
    delFirstName.value = firstNameForm;
    delLastName.value = lastNameForm;
    delAge.value = ageForm;
    deleteUserModal.show();
})


let idForm = 0;
on(document, 'click', '.btnEdit', e => {
    const row = e.target.parentNode.parentNode;
    idForm = row.children[0].innerHTML;

    const usernameForm = row.children[1].innerHTML;
    const firstNameForm = row.children[2].innerHTML;
    const lastNameForm = row.children[3].innerHTML;
    const ageForm = row.children[4].innerHTML;


    editId.value = idForm;
    editUsername.value = usernameForm;
    editFirstName.value = firstNameForm;
    editLastName.value = lastNameForm;
    editAge.value = ageForm;
    editPassword.value = ''
    editRoles.options.selectedIndex = -1;
    editUserModal.show();

})


btnCreate.addEventListener('click', () => {

    debugger;
    newFirstName.value = ''
    newLastName.value = '';
    newAge.value = '';
    newUsername.value = '';
    newPassword.value = ''
    newRoles.options.selectedIndex = -1;
    navtab1.setAttribute("class", "nav-link");
    navtab2.setAttribute("class", "nav-link active");
    tab1.setAttribute("class", "tab-pane fade");
    tab2.setAttribute("class", "tab-pane fade active show");
});


deleteUserForm.addEventListener('submit', (e) => {
    e.preventDefault();
    fetch(url + delId.value, {
        method: 'DELETE',
        headers: {
            'Content-type': 'application/json; charset=UTF-8'
        },
    })
        .then(res => res.json())
        .catch(err => console.log(err))
        .then(refreshListOfUsers);
    deleteUserModal.hide();
});


newUserForm.addEventListener('submit', (e) => {
    let rolesJ = [];
    e.preventDefault();
    const selectedOpts = [...newRoles.options]
        .filter(x => x.selected)
        .map(x => x.value);

    selectedOpts.forEach(
        role => {
            rolesJ.push(rolesArr[role - 1])
        }
    );

    const fetchFunction = async () => {
        const fetchedData = await
            fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    firstName: newFirstName.value,
                    lastName: newLastName.value,
                    age: newAge.value,
                    username: newUsername.value,
                    password: newPassword.value,
                    roles: rolesJ
                })
            });

        if (!fetchedData.ok) {
            fetchedData.json()
                .then(data => alert(data.message))
        }
        return fetchedData;
    }

    fetchFunction()
        .then(response => response.json())
        .catch(err => console.log(err))
        .then(refreshListOfUsers);


    debugger;
    navtab1.setAttribute("class", "nav-link active");
    navtab2.setAttribute("class", "nav-link");
    tab1.setAttribute("class", "tab-pane fade active show");
    tab2.setAttribute("class", "tab-pane fade");

})


editUserForm.addEventListener('submit', (e) => {
    let rolesJ = [];
    e.preventDefault();
    const selectedOpts = [...editRoles.options]
        .filter(x => x.selected)
        .map(x => x.value);

    selectedOpts.forEach(
        role => {
            rolesJ.push(rolesArr[role - 1])
        }
    );

    const fetchFunction = async () => {
        const fetchedData = await fetch(url + idForm, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: editId.value,
                firstName: editFirstName.value,
                lastName: editLastName.value,
                age: editAge.value,
                username: editUsername.value,
                password: editPassword.value,
                roles: rolesJ
            })
        });

        if (!fetchedData.ok) {
            fetchedData.json()
                .then(data => alert(data.message))
        }
        return fetchedData;
    }
    fetchFunction()
        .then(response => response.json)
        .then(refreshListOfUsers)
    editUserModal.hide();
})

window.onload = function () {
    firstTabPill.className = 'ddpills nav-link text-start active';
    firstTab.className = 'ddtabs tab-pane fade active show';
}

