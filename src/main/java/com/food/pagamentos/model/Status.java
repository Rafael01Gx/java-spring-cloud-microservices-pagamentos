package com.food.pagamentos.model;

public enum Status {
    CRIADO {
        public boolean proximoStatusValido(Status status) {
          return status == Status.CONFIRMADO || status == CANCELADO ;
        }
    },
    CONFIRMADO {
        public boolean proximoStatusValido(Status status) {
            return false ;
        }
    },
    CANCELADO {
        public boolean proximoStatusValido(Status status) {
            return false;
        }
    };

    public boolean validaProximoStatus(Status novo) {
        if (this == novo) {
            return true;
        }
        return proximoStatusValido(novo);
    }

    public abstract boolean proximoStatusValido(Status status);
}


