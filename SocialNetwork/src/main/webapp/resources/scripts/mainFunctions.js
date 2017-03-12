/**
 * Created by Rostyslav on 09.03.2017.
 */

function deleteRecord(recId){
    $.ajax({
        url: "/deleteRecord",
        data:({
            idRecord: recId
        }),
        async: false,
        success: function(data){
            var mainDiv = document.getElementById("records");
            var deletedDiv = document.getElementById(recId+' div');
            mainDiv.removeChild(deletedDiv);
        }
    })
}