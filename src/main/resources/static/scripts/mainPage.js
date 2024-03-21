function getSelectedOption(elementId) {
    let selectElement = document.getElementById(elementId);

    return selectElement.options[selectElement.selectedIndex]

}

function getSelectedOptions(elementId) {
    let selectElement = document.getElementById(elementId);
    let selectedOptions = [];
    for (let i = 0; i < selectElement.selectedOptions.length; i++) {
        selectedOptions.push(selectElement.selectedOptions[i].value);
    }
    return selectedOptions;
}

async function findDisease() {
    let symptomList = getSelectedOptions("symptomsSelect");
    try {
        const response = await fetch("/disease/find", {
            method: "POST",
            body: JSON.stringify({
                symptomList,
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        });

        if (!response.ok) {
            throw new Error("Network response was not ok.");
        }

        const data = await response.json();

        return data;
    } catch (error) {
        console.error("Error occurred while fetching data:", error);
    }
    return [];
}

async function onClickFindDiseasesButton() {
    let diseaseList = await findDisease();

    removeAllOptions("diseaseSelect")

    for (let i = 0; i < diseaseList.length; i++) {
        let disease = diseaseList[i];
        addOptionSelectElement(document.getElementById("diseaseSelect"), disease.name, disease.id);
    }
}

async function onClickFindTreatmentProtocols() {
    let protocolList = await findProtocol();
    let selectId = "treatmentProtocolSelect";
    removeAllOptions(selectId);
    for (let i = 0; i < protocolList.length; i++) {
        let protocol = protocolList[i];
        addOptionSelectElement(document.getElementById(selectId), protocol.name, JSON.stringify(protocol));
    }

}

function onClickFillWithProtocolData() {
    let selectId = "treatmentProtocolSelect";

    let treatmentProtocolSelectElement = document.getElementById(selectId);

    let selectedOption = treatmentProtocolSelectElement.options[treatmentProtocolSelectElement.selectedIndex];

    let data = JSON.parse(selectedOption.value);

    let procedures = data.procedures;


    let proceduresTextAreaElement = document.getElementById("procedures");
    proceduresTextAreaElement.value = procedures;

    let drugBlock = document.getElementById("drugBlock");

    drugBlock.innerHTML = '';

    let tpasList = data.treatmentProtocolActiveSubstances;

    for (let i = 0; i < tpasList.length; i++) {
        let tpas = tpasList[i];
        createDrugSelectionBlock(drugBlock, tpas)
    }

}


function removeAllOptions(selectId) {
    let selectElement = document.getElementById(selectId);

    while (selectElement.options.length > 0) {
        selectElement.remove(0);
    }
    // Refresh the Bootstrap Selectpicker to reflect the changes todo change this
    $('#' + selectId).selectpicker('refresh');
}


function addOptionSelectElement(selectElement, optionText, optionValue) {
    let option = document.createElement("option");
    option.text = optionText;
    option.value = optionValue;


    selectElement.add(option); // todo

    // Refresh the Bootstrap Selectpicker to reflect the changes
    $('#' + selectElement.id).selectpicker('refresh');
}


async function findProtocol() {
    let selectedOption = getSelectedOption("diseaseSelect");
    try {
        const response = await fetch("/treatment-protocol/find", {
            method: "POST",
            body: JSON.stringify({
                diseaseId: selectedOption.value,
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        });

        if (!response.ok) {
            throw new Error("Network response was not ok.");
        }

        const data = await response.json();

        return data;
    } catch (error) {
        console.error("Error occurred while fetching data:", error);
    }
    return [];
}

async function createDrugSelectionBlock(elementToAddTo, tpas) {
    let div = document.createElement("div");
    let selectElement = document.createElement("select");

    let drugList = await findDrugs(tpas.idActiveSubstanceId);

    for (let i = 0; i < drugList.length; i++) {
        let option = document.createElement("option");
        let drug = drugList[i];
        option.text = drug.name;
        option.value = drug.id;
        option.setAttribute("amount", drug.amount);
        selectElement.appendChild(option);
    }


    let activeSubstanceNameLabel = document.createElement("label");
    activeSubstanceNameLabel.innerText = tpas.activeSubstanceName + ": ";
    activeSubstanceNameLabel.className = "form-label";


    let label1 = document.createElement("label");
    label1.className = "form-label";
    label1.innerText = "Recommended amount: ";

    let recommendedAmountLabel = document.createElement("label");
    recommendedAmountLabel.className = "form-label";
    recommendedAmountLabel.innerText = tpas.recommendedAmount;

    let input = document.createElement("input");

    input.type = "number";
    input.className = ""; // form-control


    let button = document.createElement("button");
    button.innerText = "Check storage";
    button.addEventListener("click", async function () {
        let selectedOption = selectElement.options[selectElement.selectedIndex];
        let selectedValue = selectedOption.value;
        let result = await checkStorage(selectedValue, input.value);
        // Change input color based on the result
        input.style.backgroundColor = result ? "#90EE90" : "#FF2800";
    });


    div.appendChild(activeSubstanceNameLabel);
    div.appendChild(document.createElement("br"));
    div.appendChild(selectElement);
    div.appendChild(input);
    div.appendChild(button);
    div.appendChild(document.createElement("br"));
    div.appendChild(label1);
    div.appendChild(recommendedAmountLabel);

    elementToAddTo.appendChild(div);
}

async function findDrugs(activeSubstanceId) {
    try {
        const response = await fetch("/drug/find", {
            method: "POST",
            body: JSON.stringify({
                activeSubstanceId: activeSubstanceId,
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        });

        if (!response.ok) {
            throw new Error("Network response was not ok.");
        }

        return await response.json();
    } catch (error) {
        console.error("Error occurred while fetching data:", error);
    }
    return [];
}

async function checkStorage(drugId, amount) {
    try {
        const response = await fetch("/drug/check-amount", {
            method: "POST",
            body: JSON.stringify({
                drugId: drugId,
                amount: amount
            }),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        });

        if (!response.ok) {
            throw new Error("Network response was not ok.");
        }

        return await response.json();
    } catch (error) {
        console.error("Error occurred while fetching data:", error);
    }
    return {};
}


async function onClickSubmitData() {

    let patientFirstName = document.getElementById("patientFirstName").value;
    let patientLastName = document.getElementById("patientLastName").value;
    let selectedSymptoms = getSelectedOptions("symptomsSelect")
    let selectedDisease = getSelectedOption("diseaseSelect").value
    let procedures = document.getElementById("procedures").value


    let selectedDrugs = [];
    let drugBlocks = document.querySelectorAll("#drugBlock > div");
    drugBlocks.forEach(drugBlock => {
        let selectElement = drugBlock.querySelector("select");
        let selectedOption = selectElement.options[selectElement.selectedIndex];
        let drugId = selectedOption.value;
        let amount = drugBlock.querySelector("input").value;
        selectedDrugs.push({
            drugId: drugId,
            amount: amount
        });
    });


    try {
        const response = await fetch("/receipt/submit-data", {
            method: "POST",
            body: JSON.stringify(
                {
                    patientFirstName: patientFirstName,
                    patientLastName: patientLastName,
                    symptomIdList: selectedSymptoms,
                    diseaseId: selectedDisease,
                    procedures: procedures,
                    drugIdAmountList: selectedDrugs
                }
            ),
            headers: {
                "Content-type": "application/json; charset=UTF-8"
            }
        });

        if (!response.ok) {
            throw new Error("Network response was not ok.");
        }

        downloadFile(await response.text());

        //window.location.href = "/";
    } catch (error) {
        console.error("Error occurred while submitting data:", error);
    }
}

// Function to initiate file download
function downloadFile(data) {
    // Create a Blob object from the data
    const blob = new Blob([data], { type: 'text/plain' }); // Adjust the type as per your file type

    // Create a link element
    const link = document.createElement('a');

    // Set the href attribute with the Blob object
    link.href = window.URL.createObjectURL(blob);

    // Set the download attribute with desired file name
    link.download = 'receipt.txt';

    // Append the link to the body
    document.body.appendChild(link);

    // Programmatically click the link to trigger the download
    link.click();

    // Cleanup
    document.body.removeChild(link);
}

