function listContainsName(name, elementClassName) {
    let LIElements = document.getElementsByClassName(elementClassName);
    for (let i = 0; i < LIElements.length; i++) {
        if (LIElements[i].children[1].innerText.split(":")[0].trim() === name) {
            return true;
        }
    }
    return false;
}
function removeParent(element){
    element.parentNode.remove();
}
function removeListItem() {
    this.parentNode.remove();
}
function addInputToListForm() {
    let ul = document.getElementById("symptomList");
    let inputSymptomElement = document.getElementById("inputSymptom");

    if (!ul || !inputSymptomElement) {
        console.error("Some required elements are missing.");
        return;
    }


    let symptomName = inputSymptomElement.value;
    if (listContainsName(symptomName, "symptomLI")) {
        console.log("element already in list");
        return;
    }

    addSymptomToDocument(symptomName);

    inputSymptomElement.value = "";
    inputSymptomElement.focus();
}


function addSymptomToDocument(symptomName){
    let ul = document.getElementById("symptomList");
    console.log("111")

    let li = document.createElement("li");
    li.className = "symptomLI";

    let paragraphElement = document.createElement("p");
    let inputElement = document.createElement("input");

    inputElement.type = "hidden";
    inputElement.name = "symptom";


    paragraphElement.innerText = symptomName;
    inputElement.value = symptomName;

    let removeButton = document.createElement("button");
    removeButton.innerText = "Remove";
    removeButton.onclick = removeListItem;

    li.appendChild(inputElement);
    li.appendChild(paragraphElement);
    li.appendChild(removeButton);
    ul.appendChild(li);

}