const sortById = () => {
    let sorted = reimbData.sort((a,b) => {
        if(a.id < b.id){
            return -1;
        }
        if(a.id > b.id){
            return 1;
        }

        return 0;
    })
    var old_tbody = document.getElementById("reimb-table").getElementsByTagName('tbody')[0];
    var new_tbody = document.createElement('tbody');
    popRows(sorted, new_tbody);
    old_tbody.parentNode.replaceChild(new_tbody, old_tbody)
}

const sortByAmount = () => {
    let sorted = reimbData.sort((a,b) => {
        if(a.amount < b.amount){
            return -1;
        }
        if(a.amount > b.amount){
            return 1;
        }

        return 0;
    })
    var old_tbody = document.getElementById("reimb-table").getElementsByTagName('tbody')[0];
    var new_tbody = document.createElement('tbody');
    popRows(sorted, new_tbody);
    old_tbody.parentNode.replaceChild(new_tbody, old_tbody)

}
const sortByModifiedBy = () => {
    let sorted = reimbData.sort((a,b) => {
        if(a.resolver.username < b.resolver.username){
            return -1;
        }
        if(a.resolver.username> b.resolver.username){
            return 1;
        }

        return 0;
    })
    var old_tbody = document.getElementById("reimb-table").getElementsByTagName('tbody')[0];
    var new_tbody = document.createElement('tbody');
    popRows(sorted, new_tbody);
    old_tbody.parentNode.replaceChild(new_tbody, old_tbody)

}
const sortByType = () => {
    let sorted = reimbData.sort((a,b) => {
        if(a.type.id < b.type.id){
            return -1;
        }
        if(a.type.id > b.type.id){
            return 1;
        }

        return 0;
    })
    var old_tbody = document.getElementById("reimb-table").getElementsByTagName('tbody')[0];
    var new_tbody = document.createElement('tbody');
    popRows(sorted, new_tbody);
    old_tbody.parentNode.replaceChild(new_tbody, old_tbody)

}
const sortByStatus = () => {
    let sorted = reimbData.sort((a,b) => {
        if(a.status.id < b.status.id){
            return -1;
        }
        if(a.status.id > b.status.id){
            return 1;
        }

        return 0;
    })
    var old_tbody = document.getElementById("reimb-table").getElementsByTagName('tbody')[0];
    var new_tbody = document.createElement('tbody');
    popRows(sorted, new_tbody);
    old_tbody.parentNode.replaceChild(new_tbody, old_tbody)
}

const sortByDateSubmitted = () => {
    let sorted = reimbData.sort((a,b) => {
        if(a.dateSubmitted < b.dateSubmitted){
            return -1;
        }
        if(a.dateSubmitted > b.dateSubmitted){
            return 1;
        }

        return 0;
    })
    var old_tbody = document.getElementById("reimb-table").getElementsByTagName('tbody')[0];
    var new_tbody = document.createElement('tbody');
    popRows(sorted, new_tbody);
    old_tbody.parentNode.replaceChild(new_tbody, old_tbody)
}

const sortByDateModified = () => {
    let sorted = reimbData.sort((a,b) => {
        if(a.dateModified < b.dateModified){
            return -1;
        }
        if(a.dateModified > b.dateModified){
            return 1;
        }

        return 0;
    })
    var old_tbody = document.getElementById("reimb-table").getElementsByTagName('tbody')[0];
    var new_tbody = document.createElement('tbody');
    popRows(sorted, new_tbody);
    old_tbody.parentNode.replaceChild(new_tbody, old_tbody)
}
