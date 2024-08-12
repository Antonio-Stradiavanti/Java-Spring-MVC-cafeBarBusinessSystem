function onSubmit() {
    return confirm('вы действительно хотите удалить выбранные объекты ?');
}

function selectAllCheckBoxes() {
    const switchAllCheckBox = document.getElementById('switch-all');
    let checkBoxState = switchAllCheckBox.checked;

    const checkBoxes = document.getElementsByName('id');
    for (let i = 0; i < checkBoxes.length; ++i) {
        if (checkBoxes[i].type == 'checkbox') {
            checkBoxes[i].checked = checkBoxState;
        }
    }
}