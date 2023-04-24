$(document).ready(function () {
    showItemLSToCart();
    showTotalPrice();

    // lấy nội dung từ bộ nhớ cục bộ
    function getLSContent() {

        // nếu không có gì thì tạo mảng trống
        const vLSContent = JSON.parse(localStorage.getItem("products"));
        return vLSContent;
    }

    // lưu nội dung bên trong bộ nhớ cục bộ
    function setLSContent(pLSContent) {
        localStorage.setItem("products", JSON.stringify(pLSContent));
    }

    //hiển thị sản phẩm từ bộ nhớ cục bộ vào giỏ hàng
    function showItemLSToCart() {
        var vLSContent = getLSContent();
        $("#cartContent > tbody").text("");
        if (vLSContent !== null) {
            for (i = 0; i < vLSContent.length; i++) {
                bProductMarkup = `<tr class="cart_item">
                                    <td class="product-remove">
                                        <a title="Xoá sản phẩm" class="remove btn" data-product_id=${vLSContent[i].id}>X</a>
                                    </td>
                                    <td class="product-thumbnail">
                                        <a href="single-product.html?id=${vLSContent[i].id}"><img width="145" height="145"
                                                alt="poster_1_up" class="shop_thumbnail"
                                                src="img/${vLSContent[i].code}.jpg"></a>
                                    </td>

                                    <td class="product-name">
                                        <a href="single-product.html?id=${vLSContent[i].id}">${vLSContent[i].name}</a>
                                    </td>

                                    <td class="product-price">
                                        <span class="amount">${vLSContent[i].price.toLocaleString('vi', { style: 'currency', currency: 'VND' })}</span>
                                    </td>

                                    <td class="product-quantity">
                                        <div class="quantity buttons_added">
                                            <input type="button" class="minus" value="-" data-product_id=${vLSContent[i].id}>
                                            <input type="number" size="4" class="input-text qty text"
                                                title="Qty" value="${vLSContent[i].qty}" min="0" step="1">
                                            <input type="button" class="plus" value="+" data-product_id=${vLSContent[i].id}>
                                        </div>
                                    </td>

                                    <td class="product-subtotal">
                                        <span class="amount subTotalPrice">${(vLSContent[i].price * vLSContent[i].qty).toLocaleString('vi', { style: 'currency', currency: 'VND' })}</span>
                                    </td>
                                </tr>`;
                $("#cartContent").append(bProductMarkup);
            }
        } else {
            // nếu không có nội dung nào trong bộ nhớ cục bộ sẽ thông báo cho người dùng
            bProductMarkup = `<tr class="cart_item">
                                <td colspan="6"><p style="font-size: 20px">Chưa có sản phẩm trong giỏ hàng</p></td>
                            </tr>`;
            $("#cartContent").append(bProductMarkup);
        }
    }

    //  hiển thị tổng tiền
    function showTotalPrice() {
        console.log("Tổng giá tiền là: ");
        var sum = 0;
        // lặp qua từng td dựa trên lớp và thêm các giá trị
        $(".product-subtotal").each(function () {

            //convert string sang float và * thêm 1.000.000 để quy ra số tiền
            var value = parseFloat($(this).text()) * 1000000.00;
            console.log(value)
            // chỉ thêm nếu giá trị là số
            if (!isNaN(value) && value.length != 0) {
                sum += parseFloat(value);
            }
        });
        $("#cartTotal").text(sum.toLocaleString('vi', { style: 'currency', currency: 'VND' }));
        $(".cart-amunt").text($("#cartTotal").text());

    }

    //Hàm xoá sản phẩm khỏi giỏ hàng
    function removeProduct(pProductId) {
        // xóa sản phẩm khỏi giỏ hàng (và khỏi bộ nhớ cục bộ)

        //lấy danh sách sản phẩm từ LS
        var vLSContent = getLSContent();

        // lấy chỉ mục của mặt hàng sản phẩm để loại bỏ
         // bên trong mảng nội dung lưu trữ cục bộ
        let vProductIndex;
        vLSContent.forEach(function (product, i) {
            if (product.id === pProductId) {
                vProductIndex = i;
            }
        });

       // sửa đổi các mục trong mảng lưu trữ cục bộ
         // để xóa mục sản phẩm đã chọn

        vLSContent.splice(vProductIndex, 1);
        // cập nhật nội dung lưu trữ cục bộ
        setLSContent(vLSContent);
        showItemLSToCart();
    }

    //Hàm tăng số lượng sản phẩm trong giỏ
    function increaseQtyProduct(productId) {
       // xóa sản phẩm khỏi giỏ hàng (và khỏi bộ nhớ cục bộ)

         // lấy danh sách sản phẩm từ LS
        var lsContent = getLSContent();

       // lấy chỉ số của sản phẩm cần loại bỏ
         // bên trong mảng nội dung lưu trữ cục bộ
        let productIndex;
        lsContent.forEach(function (product, i) {
            if (product.id === productId) {
                productIndex = i;

            }
        });

        lsContent[productIndex].qty = lsContent[productIndex].qty + 1;
        setLSContent(lsContent);
        showItemLSToCart();
    }

    //Hàm giảm số lượng sản phẩm trong giỏ
    function decreaseQtyProduct(productId) {
        var lsContent = getLSContent();
        let productIndex;
        lsContent.forEach(function (product, i) {
            if (product.id === productId) {
                productIndex = i;

            }
        });

        if (lsContent[productIndex].qty > 1) {
            lsContent[productIndex].qty = lsContent[productIndex].qty - 1;
        }

        setLSContent(lsContent);
        showItemLSToCart();
    }

    $("#cartContent").on("click", ".plus", function () {
        var vProductId = $(this).data("product_id");
        increaseQtyProduct(vProductId);
        showTotalPrice();
    })

    $("#cartContent").on("click", ".minus", function () {
        var vProductId = $(this).data("product_id");
        decreaseQtyProduct(vProductId);
        showTotalPrice();
    })

    $("#cartContent").on("click", ".remove", function () {
        console.log("Xoá sản phẩm khỏi cart");
        var vProductId = $(this).data("product_id")
        removeProduct(vProductId);
        showTotalPrice();
    })

    
})