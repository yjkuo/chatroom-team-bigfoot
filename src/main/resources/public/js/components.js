

export function chatroomsListElement(chatroomName, isOpen) {
    var html;
    if (isOpen) html = `
        <li class="list-group-item">
            <label>${chatroomName}</label>
            <button class="btn btn-secondary btn-sm float-end btn-open-chatroom">Open</button>
        </li>
    `
    else html = `
        <li class="list-group-item">
            <label>${chatroomName}</label>
            <button class="btn btn-success btn-sm float-end btn-open-chatroom">Open</button>
        </li>
        `
    return html
}

export function userListElement(currentUser, username, admin) {
    if (username == admin) return `
            <li class="list-group-item">
                <label style="color:red">${username} (Admin)</label>
                <button class="btn btn-warning btn-sm float-end btn-ban" style="display: none">Ban</button>
            </li>
        `
    var html;
    if (currentUser == admin)
        return `
            <li class="list-group-item">
                <label>${username}</label>
                <button class="btn btn-warning btn-sm float-end btn-ban">Ban</button>
            </li>
            `

    if (username == currentUser) html = `
         <li class="list-group-item">
            <label>${username}</label>
            <button class="btn btn-warning btn-sm float-end btn-report" style="display: none">Report</button>
        </li>
    `
    else html = `
         <li class="list-group-item">
            <label>${username}</label>
            <button class="btn btn-warning btn-sm float-end btn-report">Report</button>
        </li>
    `

    return html;
}

export function rightMsgHtml (receiver, msg, id) {
    return `
        <div class="container pb-3" id="msg-${id}">
            <div class="chat-message-right">
                <div class="flex-shrink-1 rounded py-2 px-3 mr-3"> 
                    <div class="d-flex flex-row">
                        <div class="d-flex flex-column px-2">           
                            <div class="fw-bold mb-1">You to ${receiver}</div>
                            <span>${msg}</span>
                        </div>
                        <div class="d-flex flex-column">
                            <button class="btn btn-warning btn-sm w-100 edit-btn">Edit</button>
                            <button class="btn btn-danger btn-sm w-100 delete-btn" id="del">Del</button>
                        </div> 
                    </div>
                </div>                                                    
            </div>
            <div class="float-end">
                <span style="font-size:10.0pt">Delivered</span>
            </div>   
        </div>
    `;
}

export function leftMsgHtml (sender, receiver, msg, id, isAdmin) {
    let deleteBtn = isAdmin ? `<div class="d-flex flex-column">
                        <button class="btn btn-danger btn-sm delete-btn">Del</button>
                    </div>`: `<div class="d-none"></div>`;
    return `
        <div class="container pb-3" id="msg-${id}">
        <div class="chat-message-left">
        <div class="flex-shrink-1 bg-light rounded py-2 px-3 ml-3"> 
            <div class="d-flex flex-row">
                <div class="d-flex flex-column px-2">           
                    <div class="fw-bold mb-1">${sender} to ${receiver}</div>
                    ${msg}
                </div>
                 ${deleteBtn}
            </div>
        </div>
        </div>
        </div>
    `;
}

export function publicRoomListElement(roomName) {
    return `
        <li class="list-group-item">
            <label>${roomName}</label>
            <button class="btn btn-success btn-sm float-end btn-join-public-chatroom">Join</button>
        </li>
    `
}

export function privateRoomListElement(roomName) {
    return `
        <li class="list-group-item">
            <label>${roomName}</label>
            <button class="btn btn-success btn-sm float-end btn-join-private-chatroom">Join</button>
        </li>
    `
}