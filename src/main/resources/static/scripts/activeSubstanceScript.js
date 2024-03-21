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

function paragraphTextFromJSONData(data) {
    return data.substanceName.toString() + ": " + data.recommendedAmount.toString();
}




function addInputToListForm() {
    let ul = document.getElementById("activeSubstanceList");
    let selectSubstanceElement = document.getElementById("activeSubstanceSelect");
    let recommendedAmountElement = document.getElementById("recommendAmount");

    if (!ul || !selectSubstanceElement || !recommendedAmountElement) {
        console.error("Some required elements are missing.");
        return;
    }


    let substanceName = selectSubstanceElement.options[selectSubstanceElement.selectedIndex].text;
    if (listContainsName(substanceName, "symptomLI")) {
        console.log("active substance name already in list");
        return;
    }

    let recommendedAmount = recommendedAmountElement.value;

    addSubstanceNameRecommendedAmountToDocument(substanceName, recommendedAmount);

    selectSubstanceElement.selectedIndex = 0;
    recommendedAmountElement.value = "";

    selectSubstanceElement.focus();
}


function addSubstanceNameRecommendedAmountToDocument(substanceName, recommendedAmount){
    let ul = document.getElementById("activeSubstanceList");

    let li = document.createElement("li");
    li.className = "activeSubstanceLI";

    let paragraphElement = document.createElement("p");
    let inputElement = document.createElement("input");

    inputElement.type = "hidden";
    inputElement.name = "activeSubstanceAmount";


    let data = {
        substanceName: substanceName,
        recommendedAmount: recommendedAmount
    };

    paragraphElement.innerText = paragraphTextFromJSONData(data);
    inputElement.value = JSON.stringify(data);

    let removeButton = document.createElement("button");
    removeButton.innerText = "Remove";
    removeButton.onclick = removeListItem;

    li.appendChild(inputElement);
    li.appendChild(paragraphElement);
    li.appendChild(removeButton);
    ul.appendChild(li);

}