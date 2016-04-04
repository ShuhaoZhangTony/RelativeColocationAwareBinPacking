function [f,g] = f(x)
% Calculate objective f
f = 100*(x(2) - x(1)^2)^2 + (1-x(1))^2;

if nargout > 1 % gradient required
    g = [-400*(x(2)-x(1)^2)*x(1)-2*(1-x(1));
        200*(x(2)-x(1)^2)];
end
function [q]=rosenbrockwithgrad()
options = optimoptions('fminunc','Algorithm','trust-region','GradObj','on');
problem.options = options;
problem.x0 = [-1,2];
problem.objective = @f;
problem.solver = 'fminunc';
x = fminunc(problem);
end