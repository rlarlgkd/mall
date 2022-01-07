var IMP = window.IMP;
IMP.init("imp73892195");

function requestPay() {
  var sum = document.getElementById("sum").value;
  var products = document.getElementById("products").value;
  var num = document.getElementById("ccnt").value;
  var mname = document.getElementById("mname").value;

  sum = sum * num;
  IMP.request_pay({
    pg: "kakaopay",
    pay_method: "card",
    merchant_uid: uuidv4(),
    name: products,
    amount: sum,
    buyer_email: "gildong@gmail.com",
    buyer_name: mname,
    buyer_tel: "010-4242-4242",
    buyer_addr: "서울특별시 강남구 신사동",
    buyer_postcode: "01181"
  }, function (rsp) { // callback
    if (rsp.success) {
      alert('결제가 완료되었습니다.')
	  addSale();
    } else {
      alert('결제가 취소되었습니다.')

    }
  });

  function uuidv4() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
      var r = Math.random() * 16 | 0, v = c == 'x' ? r : (r & 0x3 | 0x8);
      return v.toString(16);
    });
  }
}

