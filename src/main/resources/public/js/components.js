

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