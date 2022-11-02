

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

export function userListElement(username, isUser, isAdmin) {
    var html;
    if (isAdmin) html = `
            <li class="list-group-item">
                <label style="color:red">${username} (Admin)</label>
                <button class="btn btn-warning btn-sm float-end btn-ban" style="display: none">Ban</button>
            </li>
        `
    else {
        if (isUser) html = `
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
    }
    return html;
}

export function rightMsgHtml (receiver, msg) {
    return `
        <div class="container pb-3">
            <div class="chat-message-right">
                <div class="flex-shrink-1 rounded py-2 px-3 mr-3"> 
                    <div class="d-flex flex-row">
                        <div class="d-flex flex-column px-2">           
                            <div class="fw-bold mb-1">You to ${receiver}</div>
                            ${msg}
                        </div>
                        <div class="d-flex flex-column">
                            <div><button class="btn btn-warning btn-sm w-100">Edit</button></div>
                            <div><button class="btn btn-danger btn-sm w-100">Del</button></div>
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

export function leftMsgHtml (sender, receiver, msg) {
    return `
        <div class="chat-message-left pb-4">
        <div class="flex-shrink-1 bg-light rounded py-2 px-3 ml-3"> 
            <div class="d-flex flex-row">
                <div class="d-flex flex-column px-2">           
                    <div class="fw-bold mb-1">${sender} to ${receiver}</div>
                    ${msg}
                </div>
                <div class="d-flex flex-column">
                    <div><button class="btn btn-danger btn-sm">Del</button></div>
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
            <button class="btn btn-success btn-sm float-end btn-join-chatroom">Join</button>
        </li>
    `
}