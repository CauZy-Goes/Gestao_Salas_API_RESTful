insert into gestao_espaco_fisico.client (
    client_id,
    client_secret,
    redirect_uri,
    scope
) values (
             'teste1',
             '$2a$12$bj06Or4xZ4pPE/pMeCt2YuvY7AUsvu.KUfkP5Ej5vFauNZ2ZleaS2', -- senha: 123
             'http://localhost:8081/authorized',
             'read write'
         );
