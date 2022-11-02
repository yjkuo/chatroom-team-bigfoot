

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
                <label style="color:red">${username}</label>
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