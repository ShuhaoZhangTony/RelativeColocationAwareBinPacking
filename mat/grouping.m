function y=grouping(e,f,g)
    n=4;%number of CPU sockets.
    m=6;%number of Executors.
    E=[1,1,2,2,2,2];%the allocation plan of Executors to Sockets.
    C=50;%The available CPU cycles per unit of time.
    R=10;%External input events for first unit of time.
    P=zeros(m); 
    Topo=zeros(m);
    Topo(1,2)=1;
    Topo(1,3)=1;
    
    P(1,2)=e;
    P(1,3)=1-e;    
    
    Topo(2,4)=1;
    Topo(2,5)=1;
    
    P(2,4)=f;
    P(2,5)=1-f;
    
    Topo(3,4)=1;
    Topo(3,5)=1;
            
    P(3,4)=g;
    P(3,5)=1-g;
    
    Topo(4,6)=1;
    Topo(5,6)=1;
    %view(biograph(Topo))
    %P: The partition plan, 2-D array.
    y=Lat(Topo,P,R,C,n,m,E);
end

function [y]=sel(j)
    s=[1,0.8,0.8,0.6,0.6,1];
    y=s(j);
end

function [y]=w(j)
    c=[1,12,4,6,2,1];
    y=c(j);
end

function [y]=Lat(Topo,P,R,C,n,m,E)

    tmp=zeros(n,1);

    for i=1:n
        tmp(i)=CE(Topo,P,R,C,i,m,E);
    end

    y=max(tmp);

end

function [y]=CE(Topo,P,R,C,i,m,E)

    y=max(0,L(Topo,P,R,i,m,E)-C);

end


function [y]=L(Topo,P,R,i,m,E)
    y=0;
    for j=1:m
        if E(j)==i
        y=y+l(Topo,P,R,i,j,m,E);
        end
    end
end

function [r]=isparent(Topo,j,j_)
    r=Topo(j,j_);
end

function [r]=ischild(Topo,j,j_)
    r=Topo(j_,j);
end

function [r]=findAllparent(Topo,m,j)
        r=[];
        for j_=1:m
            if(isparent(Topo,j_,j)==1)
               r=[r,j_];
            end
        end
end

function [r]=findAllchild(Topo,j)
    A=[];
    for(j_=1:m)
        if(ischild(j_,j)==1)
            A=[A,j_];
        end
    end    
end

function [r]=findAllremotechild(Topo,m,j,E)
    r=[];
    for(j_=1:m)
        if(ischild(Topo,j_,j)==1&&remote(E,j,j_))
            r=[r,j_];
        end
    end    
end

function [r]=remote(E,j,j_)
    r=(E(j)~=E(j_));
end

function [y]=l(Topo,P,R,i,j,m,E)
    r=5;%remote push cost
    input=I(Topo,P,R,m,j);%depends on previous executors's partition plan.
    output=input*sel(j);
    h1=input*w(j);
    h2=0;
    RA=findAllremotechild(Topo,m,j,E);
    
 for idx = 1:numel(RA)
    j_ = RA(idx);
    h2=h2+output*P(j,j_)*r; 
 end
 
 y=h1+h2;

end

function [y]=I(Topo,P,R,m,j)
    if(j==1)
        y=R;   
    else
        A=findAllparent(Topo,m,j);
        y=0;
        for idx = 1:numel(A)
            j_ = A(idx);
            y=y+P(j_,j)*I(Topo,P,R,m,j_)*sel(j_);
        end
    end
end

function [res]=group()
options = optimoptions('fminunc','Algorithm','trust-region','GradObj','on');
problem.options = options;
problem.x0 = [0.5,0.5,0.5];
problem.objective = @grouping;
problem.solver = 'fminunc';

startTime = tic;
x = fminunc(problem)
time_fmincon_sequential = toc(startTime);
fprintf('Serial FMINCON optimization takes %g seconds.\n',time_fmincon_sequential);
end
