/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { StatisticMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/statistic-my-suffix/statistic-my-suffix-delete-dialog.component';
import { StatisticMySuffixService } from '../../../../../../main/webapp/app/entities/statistic-my-suffix/statistic-my-suffix.service';

describe('Component Tests', () => {

    describe('StatisticMySuffix Management Delete Component', () => {
        let comp: StatisticMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<StatisticMySuffixDeleteDialogComponent>;
        let service: StatisticMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [StatisticMySuffixDeleteDialogComponent],
                providers: [
                    StatisticMySuffixService
                ]
            })
            .overrideTemplate(StatisticMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StatisticMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StatisticMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
