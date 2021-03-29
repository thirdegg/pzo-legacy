package MessageSystem;

import Base.Address;


public class AddressService {
    private Address addressFrontend;
    private Address addressGM;
    private Address addressAS;

    public Address getAddressAS() {
        return addressAS;
    }

    public void setAddressAS(Address addressAS) {
        this.addressAS = addressAS;
    }

    public Address getAddressFrontend() {
        return addressFrontend;
    }

    public void setAddressFrontend(Address addressFrontend) {
        this.addressFrontend = addressFrontend;
    }

    public Address getAddressGameMechanics() {
        return addressGM;
    }

    public void setAddressGemeMechanics(Address addressGM) {
        this.addressGM = addressGM;
    }
}
