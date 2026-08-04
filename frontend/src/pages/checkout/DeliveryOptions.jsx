import api from '../../api/axios';
import dayjs from 'dayjs';
import { formatMoney } from '../../utils/money';
export function DeliveryOptions({ cartItem, deliveryOptions, loadCart }) {
    return (
        <div className="delivery-options">
            <div className="delivery-options-title">
                Choose a delivery option:
            </div>
            {deliveryOptions.map((deliveryOption) => {
                let priceString = 'FREE Shipping';

                if (deliveryOption.priceCents > 0) {
                    priceString = `${formatMoney(deliveryOption.priceCents)} - Shipping`;
                }
                const updateDeliveryOption = async () => {
                    await api.put(`/cart/${cartItem.id}/delivery-option`, {
                        deliveryOptionId: deliveryOption.id
                    });
                    await loadCart();

                };
                return (
                    <div key={deliveryOption.id} className="delivery-option" >
                        
                        <input type="radio"
                            checked={deliveryOption.id === cartItem.deliveryOptionId}
                            onChange={updateDeliveryOption}
                            className="delivery-option-input"
                            name={`delivery-option-${cartItem.id}`} />
                        <div>
                            <div className="delivery-option-date">
                                {dayjs(deliveryOption.estimatedDeliveryTime).format('dddd, MMMM D')}

                            </div>
                            <div className="delivery-option-price">
                                {priceString}
                            </div>
                        </div>
                    </div>

                );
            })}
        </div>

    );
}