/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { Hochzeitclick11TestModule } from '../../../test.module';
import { GalleryMySuffixComponent } from '../../../../../../main/webapp/app/entities/gallery-my-suffix/gallery-my-suffix.component';
import { GalleryMySuffixService } from '../../../../../../main/webapp/app/entities/gallery-my-suffix/gallery-my-suffix.service';
import { GalleryMySuffix } from '../../../../../../main/webapp/app/entities/gallery-my-suffix/gallery-my-suffix.model';

describe('Component Tests', () => {

    describe('GalleryMySuffix Management Component', () => {
        let comp: GalleryMySuffixComponent;
        let fixture: ComponentFixture<GalleryMySuffixComponent>;
        let service: GalleryMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [Hochzeitclick11TestModule],
                declarations: [GalleryMySuffixComponent],
                providers: [
                    GalleryMySuffixService
                ]
            })
            .overrideTemplate(GalleryMySuffixComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(GalleryMySuffixComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GalleryMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new GalleryMySuffix(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.galleries[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
