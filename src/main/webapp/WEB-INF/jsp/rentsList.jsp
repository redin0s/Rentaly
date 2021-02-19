<ul class="list-group">
    <c:forEach items="${rents}" var="r">
        <!--TODO if empty display something to say it's empty-->
        <div class="card list-group-item list-group-item-action justify-content-between align-items-center">
            <div class="card-body">
                <div class="row">
                    <div class="col-md-6">
                        <h4 id="title">${r.realty.type} a ${r.realty.city}</h4>
                        <h6 class="text-muted mb-2" id="street">${r.realty.address}</h6>
                        <c:choose>
                            <c:when test="">
                                <h6 class="text-muted mb-2" id="holder">Inquilino: ${r.holder.email}</h6>
                            </c:when>
                            <c:otherwise>
                                <h6 class="text-muted mb-2" id="holder">Proprietario: ${r.realty.owner.email}</h6>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-md-6">
                        <button class="btn btn-danger" id="remove" onclick="setSelected(${r.id});">Elimina affitto</button>
                        <h6 class="text-muted mb-2" id="cost">Affitto mensile: ${r.cost}</h6>
                        <h6 class="text-muted mb-2" id="start">Data di inizio: ${r.start}</h6>
                        <h6 class="text-muted mb-2" id="end">Data di fine: ${r.end}</h6>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</ul>